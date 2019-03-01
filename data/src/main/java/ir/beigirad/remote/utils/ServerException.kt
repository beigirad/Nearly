package ir.pallet.remote.utils

import ir.beigirad.remote.model.ResponseParent
import retrofit2.Response

class ServerException private constructor(message: String?, private val kind: Kind) : RuntimeException(message) {

    enum class Kind {
        // A non successful response in received data in 200 status code.
        RESPONSE,
        // An [IOException] occurred while communicating to the server.
        NETWORK,
        // A non-200 HTTP status code was received from the server.
        HTTP,
        // An internal error occurred while attempting to execute a request. It is best practice to
        // re-throw this exception so your application crashes.
        UNEXPECTED
    }

    companion object {

        fun httpError(response: Response<*>): ServerException {
            val message = "Error: " + response.code().toString() + " " + response.message()
            return ServerException(message, Kind.HTTP)
        }

        fun networkError(exception: Throwable): ServerException {
            return ServerException(exception.message, Kind.NETWORK)
        }

        fun networkError(message: String): ServerException {
            return ServerException(message, Kind.NETWORK)
        }

        fun unexpectedError(exception: Throwable): ServerException {
            return ServerException(exception.message, Kind.UNEXPECTED)
        }

        fun responseError(response: ResponseParent<*>?): ServerException {
//            val message =
//                if (response() == null || response.getMessage().isEmpty()) Const.SERVER_ERROR else response.getMessage()
            return ServerException("Code ${response?.meta?.code} - ${response?.meta?.errorType}", Kind.RESPONSE)
        }
    }
}