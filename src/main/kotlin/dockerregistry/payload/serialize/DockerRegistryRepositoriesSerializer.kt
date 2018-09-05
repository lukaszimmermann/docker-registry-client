package dockerregistry.payload.serialize

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import dockerregistry.payload.DockerRegistryRepositories


class DockerRegistryRepositoriesSerializer
@JvmOverloads constructor(clazz: Class<DockerRegistryRepositories>? = null): StdSerializer<DockerRegistryRepositories>(clazz) {

    override fun serialize(value: DockerRegistryRepositories, gen: JsonGenerator, provider: SerializerProvider) {

        gen.writeStartObject()
        gen.writeFieldName("repositories")
        gen.writeStartArray()
        value.forEach(gen::writeString)
        gen.writeEndArray()
        gen.writeEndObject()
    }
}