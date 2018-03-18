package ru.alexandrkutashov.onetimesecret.presentation.share

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.alexandrkutashov.onetimesecret.R
import ru.alexandrkutashov.onetimesecret.ext.toast


/**
 * @author Alexandr Kutashov
 *         on 23.02.2018
 */

class ShareFragment : MvpAppCompatFragment(), ShareView {

    @InjectPresenter
    lateinit var presenter: SharePresenter

    companion object {
        val screenKey: String = ShareFragment::class.simpleName!!
        fun newInstance(): Fragment = ShareFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_share, container, false)

        val secretText = view.findViewById<EditText>(R.id.secret_text)

        view.findViewById<Button>(R.id.share_button)
                .setOnClickListener({
            presenter.shareSecret(secretText.text.toString())
        })

        return view
    }

    override fun onShareError(message: String?) {
        val text = message ?: getString(R.string.operation_undefined_error)
        context.toast(text)
    }

    override fun onShareSuccess(link: String?) {
        link?.let { copyLink(it) }
        context.toast(R.string.link_copied)
    }

    private fun copyLink(link: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Secret for sharing", link)
        clipboard.primaryClip = clip
    }
}
