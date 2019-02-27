package ir.beigirad.remote.utils

import io.reactivex.functions.Function
import ir.beigirad.remote.model.ResponseParent
import ir.pallet.remote.utils.ServerException
import retrofit2.Response

class HttpErrorHandler<T> : Function<Response<ResponseParent<T>>, T> {
    override fun apply(response: Response<ResponseParent<T>>): T {
        if (response.isSuccessful)
            if (response.body()!!.isSuccessful)
                return response.body()?.data!!
            else
                throw ServerException.responseError(response.body())
        else
            throw ServerException.httpError(response)
    }
}