package cn.coostack.cooparticlesapi.network.buffer

import cn.coostack.cooparticlesapi.utils.RelativeLocation
import io.netty.buffer.Unpooled
import net.minecraft.util.Identifier
import net.minecraft.util.math.Vec3d
import java.util.UUID

object ParticleControlerDataBuffers {

    val registerBuilder = HashMap<ParticleControlerDataBuffer.Id, Class<out ParticleControlerDataBuffer<*>>>()

    fun boolean(value: Boolean): BooleanControlerBuffer = BooleanControlerBuffer().apply { loadedValue = (value) }
    fun char(value: Char): CharControlerBuffer = CharControlerBuffer().apply { loadedValue = (value) }
    fun string(value: String): StringControlerBuffer = StringControlerBuffer().apply { loadedValue = (value) }
    fun int(value: Int): IntControlerBuffer = IntControlerBuffer().apply { loadedValue = (value) }
    fun intArray(value: IntArray): IntArrayControlerBuffer = IntArrayControlerBuffer().apply { loadedValue = (value) }
    fun double(value: Double): DoubleControlerBuffer = DoubleControlerBuffer().apply { loadedValue = (value) }
    fun float(value: Float): FloatControlerBuffer = FloatControlerBuffer().apply { loadedValue = (value) }
    fun long(value: Long): LongControlerBuffer = LongControlerBuffer().apply { loadedValue = (value) }
    fun longArray(value: LongArray): LongArrayControlerBuffer =
        LongArrayControlerBuffer().apply { loadedValue = (value) }

    fun short(value: Short): ShortControlerBuffer = ShortControlerBuffer().apply { loadedValue = (value) }
    fun vec3d(value: Vec3d): Vec3dControlerBuffer = Vec3dControlerBuffer().apply { loadedValue = (value) }
    fun relative(value: RelativeLocation): RelativeLocationControlerBuffer =
        RelativeLocationControlerBuffer().apply { loadedValue = (value) }

    fun uuid(value: UUID): UUIDControlerBuffer = UUIDControlerBuffer().apply { loadedValue = (value) }
    fun empty(): EmptyControlerBuffer = EmptyControlerBuffer()

    /**
     * 如果某个style使用了嵌套 (内容使用了其他Style或者group)
     * 制作数据同步时 可以在最外层style加入 subGroup的 data buffer
     * 如果你使用了自定义的ParticleControlableDataBuffer
     * 那么你的buffer一定要通过 ParticleControlableDataBuffer.register(id, buffer::class.java)注册类型 否则会出现npe
     */
    fun nested(value: Map<String, ParticleControlerDataBuffer<*>>): NestedBuffersControlerBuffer =
        NestedBuffersControlerBuffer().apply { loadedValue = value }

    fun withType(value: Any, clazz: Class<out ParticleControlerDataBuffer<*>>): ParticleControlerDataBuffer<*> {
        val bufferCodec = clazz.getConstructor().newInstance() as ParticleControlerDataBuffer<Any>
        bufferCodec.loadedValue = value
        return bufferCodec
    }

    fun withDecode(buf: ByteArray, clazz: Class<out ParticleControlerDataBuffer<*>>): ParticleControlerDataBuffer<*> {
        val bufferCodec = clazz.getConstructor().newInstance() as ParticleControlerDataBuffer<Any>
        bufferCodec.loadedValue = bufferCodec.decode(buf)
        return bufferCodec
    }


    fun withId(id: ParticleControlerDataBuffer.Id, value: Any): ParticleControlerDataBuffer<*>? {
        val clazz = registerBuilder[id] ?: return null
        return withType(value, clazz)
    }

    fun withIdDecode(id: ParticleControlerDataBuffer.Id, array: ByteArray): ParticleControlerDataBuffer<*>? {
        val clazz = registerBuilder[id] ?: return null
        return withDecode(array, clazz)
    }

    fun withId(id: Identifier, value: Any): ParticleControlerDataBuffer<*>? {
        val identifier = ParticleControlerDataBuffer.Id(id)
        val clazz = registerBuilder[identifier] ?: return null
        return withType(value, clazz)
    }

    fun withIdDecode(id: Identifier, array: ByteArray): ParticleControlerDataBuffer<*>? {
        val identifier = ParticleControlerDataBuffer.Id(id)
        val clazz = registerBuilder[identifier] ?: return null
        return withDecode(array, clazz)
    }

    fun register(id: ParticleControlerDataBuffer.Id, type: Class<out ParticleControlerDataBuffer<*>>) {
        registerBuilder[id] = type
    }

    fun <T> encode(buffer: ParticleControlerDataBuffer<T>): ByteArray {
        val code = buffer.encode() ?: throw IllegalStateException("buffer encode value is null")
        val decoderID = buffer::class.java.name
        val byteBuf = Unpooled.buffer()
        byteBuf.writeInt(decoderID.length)
        byteBuf.writeBytes(decoderID.toByteArray(Charsets.UTF_8))
        byteBuf.writeBytes(code)
        return byteBuf.copy().array()
    }

    fun <T> encode(value: T, buffer: ParticleControlerDataBuffer<T>): ByteArray {
        val code = buffer.encode(value)
        val decoderID = buffer::class.java.name
        val byteBuf = Unpooled.buffer()
        byteBuf.writeInt(decoderID.length)
        byteBuf.writeBytes(decoderID.toByteArray(Charsets.UTF_8))
        byteBuf.writeBytes(code)
        return byteBuf.copy().array()
    }

    fun <T> decode(bytes: ByteArray): T {
        // 要求目标编码器必须具有空构造函数
        val byteBuf = Unpooled.wrappedBuffer(bytes)
        val len = byteBuf.readInt()
        val toStringBytes = ByteArray(len)
        byteBuf.readBytes(toStringBytes)
        val decoderID = String(toStringBytes, Charsets.UTF_8)
        val code = byteBuf.readBytes(byteBuf.readableBytes())
        val codeBytes = ByteArray(code.readableBytes())
        code.readBytes(codeBytes)
        val clazz = Class.forName(decoderID)
        val ins = clazz.getConstructor().newInstance() as ParticleControlerDataBuffer<T>

        return ins.decode(codeBytes)
    }

    fun <T> decodeToBuffer(bytes: ByteArray): ParticleControlerDataBuffer<T> {
        // 要求目标编码器必须具有空构造函数
        val byteBuf = Unpooled.wrappedBuffer(bytes)
        val len = byteBuf.readInt()
        val toStringBytes = ByteArray(len)
        byteBuf.readBytes(toStringBytes)
        val decoderID = String(toStringBytes, Charsets.UTF_8)
        val code = byteBuf.readBytes(byteBuf.readableBytes())
        val codeBytes = ByteArray(code.readableBytes())
        code.readBytes(codeBytes)
        val clazz = Class.forName(decoderID)
        val ins = clazz.getConstructor().newInstance() as ParticleControlerDataBuffer<T>

        return ins.apply { loadedValue = (ins.decode(codeBytes)) }
    }


    init {
        register(BooleanControlerBuffer.id, BooleanControlerBuffer::class.java)
        register(LongControlerBuffer.id, LongControlerBuffer::class.java)
        register(IntControlerBuffer.id, IntControlerBuffer::class.java)
        register(DoubleControlerBuffer.id, DoubleControlerBuffer::class.java)
        register(FloatControlerBuffer.id, FloatControlerBuffer::class.java)
        register(StringControlerBuffer.id, StringControlerBuffer::class.java)
        register(IntArrayControlerBuffer.id, IntArrayControlerBuffer::class.java)
        register(LongArrayControlerBuffer.id, LongArrayControlerBuffer::class.java)
        register(UUIDControlerBuffer.id, UUIDControlerBuffer::class.java)
        register(Vec3dControlerBuffer.id, Vec3dControlerBuffer::class.java)
        register(ShortControlerBuffer.id, ShortControlerBuffer::class.java)
        register(EmptyControlerBuffer.id, EmptyControlerBuffer::class.java)
        register(CharControlerBuffer.id, CharControlerBuffer::class.java)
        register(NestedBuffersControlerBuffer.id, NestedBuffersControlerBuffer::class.java)
    }

}