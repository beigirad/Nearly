package ir.beigirad.presentation.state

class Resource<out T>(
    val status: ResourceState,
    val data: T?,
    val message: String?
) {
    val hasData: Boolean
        get() = when (data) {
            null -> false
            is List<*> -> !data.isNullOrEmpty()
            else -> true
        }
}