package jdregistry.client.payload

data class DockerRepositoryName(
    val firstPathComponent: String,
    private val moreComponents: List<String> = emptyList()
) {
    init {
        // First component must be a valid path component
        require(isValidPathComponent(firstPathComponent)) {

            "The first Path Component of the DockerRepositoryName is not valid: $firstPathComponent"
        }
        require(moreComponents.all { isValidPathComponent(it) }) {

            "One of the additional Path Components is not valid"
        }

        // The total length is determined by the length of the first component plus the total
        // length of the other comoponents plus the number of '/' signs, which is equal to the
        // number of moreComponents (as each of those is prefixed by '/)'
        val totalLength = firstPathComponent.length + moreComponents.sumBy { it.length } + moreComponents.size

        require(totalLength < 256) {

            "The maximal length of the DockerRepositoryName is exceeded. Allowed: 255, have: $totalLength"
        }
    }

    val numberOfComponents = 1 + moreComponents.size
    val hasMoreComponents = numberOfComponents > 1

    operator fun get(index: Int) = if (index == 0) firstPathComponent else moreComponents[index]

    fun asString() = firstPathComponent +
            moreComponents
                    .joinToString(SEP)
                    .let { if (it.isEmpty()) "" else "$SEP$it" } // append separator

    override fun toString() = asString()

    companion object {

        private const val SEP = "/"
        private val pathComponentRegex = Regex("[a-z0-9]+(?:[._-][a-z0-9]+)*")
        private fun isValidPathComponent(item: String) = item.length < 30 && item.matches(pathComponentRegex)

        fun of(input: String): DockerRepositoryName {

            val spt = input.split(SEP)
            return DockerRepositoryName(spt[0], spt.drop(1))
        }
    }
}
