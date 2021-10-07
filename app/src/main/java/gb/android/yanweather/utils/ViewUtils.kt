package gb.android.yanweather.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar


fun View.showSnackbar(
    messageId: Int,
    length: Int
) {
    Snackbar
        .make(
            this,
            this.context.getString(messageId),
            length
        )
        .show()
}

fun View.showSnackbarAnchorView(
    messageId: Int,
    length: Int,
    view: View
) {
    Snackbar
        .make(
            this,
            this.context.getString(messageId),
            length
        )
        .setAnchorView(view)
        .show()
}

fun View.showActionSnackbar(
    messageId: Int,
    length: Int,
    messageActionId: Int,
    listener: View.OnClickListener
) {
    Snackbar
        .make(
            this,
            this.context.getString(messageId),
            length
        )
        .setAction(
            this.context.getString(messageActionId),
            listener
        )
        .show()
}