package cn.coostack.network.buffer

import io.netty.buffer.Unpooled
import net.minecraft.util.math.Vec3d
import java.util.UUID

object ParticleControlerDataBuffers {
    fun boolean(value: Boolean): BooleanControlerBuffer = BooleanControlerBuffer().apply { loadedValue = (value) }

    fun char(value: Char): CharControlerBuffer = CharControlerBuffer().apply { loadedValue = (value) }

    fun string(value: String): StringControlerBuffer = StringControlerBuffer().apply { loadedValue = (value) }

    fun int(value: Int): IntControlerBuffer = IntControlerBuffer().apply { loadedValue = (value) }

    fun double(value: Double): DoubleControlerBuffer = DoubleControlerBuffer().apply { loadedValue = (value) }

    fun float(value: Float): FloatControlerBuffer = FloatControlerBuffer().apply { loadedValue = (value) }

    fun long(value: Long): LongControlerBuffer = LongControlerBuffer().apply { loadedValue = (value) }

    fun short(value: Short): ShortControlerBuffer = ShortControlerBuffer().apply { loadedValue = (value) }

    fun vec3d(value: Vec3d): Vec3dControlerBuffer = Vec3dControlerBuffer().apply { loadedValue = (value) }
    fun uuid(value: UUID): UUIDControlerBuffer = UUIDControlerBuffer().apply { loadedValue = (value) }

    fun empty(): EmptyControlerBuffer = EmptyControlerBuffer()

    fun withType(value: Any, clazz: Class<ParticleControlerDataBuffer<*>>): ParticleControlerDataBuffer<*> {
        val bufferCodec = clazz.getConstructor(value::class.java).newInstance(value)
        return bufferCodec
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

}