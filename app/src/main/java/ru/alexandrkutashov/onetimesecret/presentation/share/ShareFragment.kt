package ru.alexandrkutashov.onetimesecret.presentation.share

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.alexandrkutashov.onetimesecret.R


/**
 * @author Alexandr Kutashov
 *         on 23.02.2018
 */

class ShareFragment : MvpAppCompatFragment(), ShareView {

    @InjectPresenter
    lateinit var presenter: SharePresenter

    companion object {
        private const val TEXT_TO_SHARE = "textToShare"
        val screenKey: String = ShareFragment::class.simpleName!!
        fun newInstance(text: String?): Fragment {
            val fragment = ShareFragment()
            text?.let {
                val bundle = Bundle()
                bundle.putString(TEXT_TO_SHARE, it)
                fragment.arguments = bundle
            }
            return fragment
        }
    }

    private lateinit var secretText: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_share, container, false)

        secretText = view.findViewById(R.id.secret_text)
        if (arguments != null && arguments.containsKey(TEXT_TO_SHARE)) {
            secretText.append(arguments.getString(TEXT_TO_SHARE))
        }

        view.findViewById<Button>(R.id.share_button)
                .setOnClickListener({
                    presenter.shareSecret(secretText.text.toString())
                })

        return view
    }

    override fun onShareError(message: String?) {
        val text = message ?: getString(R.string.operation_undefined_error)
        Snackbar.make(secretText, text, Snackbar.LENGTH_LONG).show()
    }

    override fun onShareSuccess(link: String?) {
        link?.let { copyLink(it) }
        Snackbar.make(secretText, R.string.link_copied, Snackbar.LENGTH_LONG).show()
    }

    private fun copyLink(link: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Secret for sharing", link)
        clipboard.primaryClip = clip
    }
}
