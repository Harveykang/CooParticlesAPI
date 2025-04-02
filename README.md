# 基本用法

创建一个粒子类并且继承 ControlableParticle

### Example Particle

```kotlin 
    class TestEndRodParticle(
    // Particle粒子需要的参数
    world: ClientWorld,
    pos: Vec3d,
    velocity: Vec3d,
    // 用于获取ParticleControler的粒子唯一标识符
    controlUUID: UUID,
    val provider: SpriteProvider
) :
// 必须继承 ControlableParticle类
    ControlableParticle(world, pos, velocity, controlUUID) {
    override fun getType(): ParticleTextureSheet {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE
    }

    init {
        setSprite(provider.getSprite(0, 120))
        // 由于ControlableParticle 禁止重写 tick方法
        // 使用此方法代替
        controler.addPreTickAction {
            setSpriteForAge(provider)
        }
    }

    // 基本粒子注册
    class Factory(val provider: SpriteProvider) : ParticleFactory<TestEndRodEffect> {
        override fun createParticle(
            parameters: TestEndRodEffect,
            world: ClientWorld,
            x: Double,
            y: Double,
            z: Double,
            velocityX: Double,
            velocityY: Double,
            velocityZ: Double
        ): Particle {
            return TestEndRodParticle(
                world,
                Vec3d(x, y, z),
                Vec3d(velocityX, velocityY, velocityZ),
                parameters.controlUUID,
                provider
            )
        }
    }
}
```

为了能够获取到对应的 UUID 所以你的ParticleEffect也要有uuid

```kotlin
// 作为构造参数
class TestEndRodEffect(val controlUUID: UUID) : ParticleEffect {
    companion object {
        @JvmStatic
        val codec: MapCodec<TestEndRodEffect> = RecordCodecBuilder.mapCodec {
            return@mapCodec it.group(
                Codec.BYTE_BUFFER.fieldOf("uuid").forGetter { effect ->
                    val toString = effect.controlUUID.toString()
                    val buffer = Unpooled.buffer()
                    buffer.writeBytes(toString.toByteArray())
                    buffer.nioBuffer()
                }
            ).apply(it) { buf ->
                TestEndRodEffect(
                    UUID.fromString(
                        String(buf.array())
                    )
                )
            }
        }

        @JvmStatic
        val packetCode: PacketCodec<RegistryByteBuf, TestEndRodEffect> = PacketCodec.of(
            { effect, buf ->
                buf.writeUuid(effect.controlUUID)
            }, {
                TestEndRodEffect(it.readUuid())
            }
        )

    }

    override fun getType(): ParticleType<*> {
        return ModParticles.testEndRod
    }
}
```

使用Fabric API 在客户端处注册此粒子后
接下来进行粒子组合 (ControlableParticleGroup) 的构建

### 构建 ControlableParticleGroup

ControlableParticleGroup的作用是在玩家客户端处渲染粒子组合

构建一个基本的ControlableParticleGroup代码示例:
一个在玩家视野正中心 每tick旋转10度的魔法阵

```kotlin
class TestGroupClient(uuid: UUID, val bindPlayer: UUID) : ControlableParticleGroup(uuid) {

    // 为了让服务器能够正常的将ParticleGroup数据转发给每一个玩家
    // 服务器会发 PacketParticleGroupS2C 数据包
    // 这里是解码
    class Provider : ControlableParticleGroupProvider {
        override fun createGroup(
            uuid: UUID,
            // 这里的 args是 服务器同步给客户端用的参数
            // 可以查看 cn.coostack.network.packet.PacketParticleGroupS2C 类注释的字段不建议覆盖也无需处理(已经处理好了)
            args: Map<String, ParticleControlerDataBuffer<*>>
        ): ControlableParticleGroup {
            // 绑定到的玩家
            val bindUUID = args["bindUUID"]!!.loadedValue as UUID
            return TestGroupClient(uuid, bindUUID)
        }
    }

    // 魔法阵粒子组合
    override fun loadParticleLocations(): Map<RelativeLocation, ParticleRelativeData> {
        // 在XZ平面的魔法阵
        val list = Math3DUtil.getCycloidGraphic(3.0, 5.0, 2, -3, 360, 0.2).onEach { it.y += 6 }
        // 
        return list.associateWith {
            withEffect({
                // 提供ParticleEffect (在display方法中 world.addParticle)使用
                // it类型为UUID
                TestEndRodEffect(it)
            }) {
                // this is ControlableParticle
                // 用于初始化粒子信息
                color = Vector3f(230 / 255f, 130 / 255f, 60 / 255f)
                this.maxAliveTick = this.maxAliveTick
            }
        }
    }


    /**
     * 当粒子第一次渲染在玩家视角的时候
     * 玩家超出渲染范围后又回归渲染范围任然会调用一次
     * 可以理解为粒子组初始化
     */
    override fun onGroupDisplay() {
        MinecraftClient.getInstance().player?.sendMessage(Text.of("发送粒子: ${this::class.java.name} 成功"))
        addPreTickAction {
            // 当玩家能够看到粒子的时候 (这个类会被构造)
            val bindPlayerEntity = world!!.getPlayerByUuid(bindPlayer) ?: let {
                return@addPreTickAction
            }
            teleportGroupTo(bindPlayerEntity.eyePos)
            rotateToWithAngle(
                RelativeLocation.of(bindPlayerEntity.rotationVector),
                Math.toRadians(10.0)
            )
        }
    }
}
```

创建好ControlableParticleGroup后, 需要在客户端进行注册

```kotlin
ClientParticleGroupManager.register(
    TestGroupClient::class.java, TestGroupClient.Provider()
)
```

当你完成上述操作后, 为了让其他玩家也能同步操作, 需要设置一个服务器向的ControlableParticleGroup
示例:

```kotlin
/**
 * 构造参数无要求
 */
class TestParticleGroup(private val bindPlayer: ServerPlayerEntity) :
// 第一个参数是 ParticleGroup的唯一标识符
// 这个内容会同步到客户端
// 第二个参数是粒子的可见范围
// 当玩家超出这个范围时会发送删除粒子组包(对该玩家不可见)
    ServerParticleGroup(UUID.randomUUID(), 16.0) {
    override fun tick() {
        withPlayerStats(bindPlayer)
        setPosOnServer(bindPlayer.eyePos)
    }

    /**
     * 这个是你想发送给客户端用于构建ControlableParticleGroup的参数
     * 最终会传入 ControlableParticleGroupProvider.createGroup()
     */
    override fun otherPacketArgs(): Map<String, ParticleControlerDataBuffer<out Any>> {
        return mapOf(
            "bindUUID" to ParticleControlerDataBuffers.uuid(bindPlayer.uuid)
        )
    }
}
```

完成上述构建后,只需要在服务器中添加粒子

```kotlin
val serverGroup = TestParticleGroup(user as ServerPlayerEntity)
ServerParticleGroupManager.addParticleGroup(
    TestGroupClient::class.java,
    //                      world必须是ServerWorld
    serverGroup, user.pos, world as ServerWorld
)
```

其余特殊用法可以查看
cn.coostack.particles.control.group.ControlableParticleGroup 与

cn.coostack.network.particle.ServerParticleGroup