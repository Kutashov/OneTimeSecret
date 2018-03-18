package ru.alexandrkutashov.onetimesecret.presentation.read

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.alexandrkutashov.onetimesecret.R
import ru.alexandrkutashov.onetimesecret.ext.toast

/**
 * @author Alexandr Kutashov
 * on 17.03.2018
 */
class ReadFragment : MvpAppCompatFragment(), ReadView {

    @InjectPresenter
    lateinit var presenter: ReadPresenter

    companion object {
        private const val SECRET_LINK = "secret_link"
        val screenKey: String = ReadFragment::class.simpleName!!

        fun newInstance(link: String): Fragment {
            val fragment = ReadFragment()
            val bundle = Bundle()
            bundle.putString(SECRET_LINK, link)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var secret: TextView
    private lateinit var readBtn: Button
    private var secretLink: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        secretLink = arguments.getString(SECRET_LINK)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_read, container, false)

        secret = view.findViewById(R.id.secret_text)
        readBtn = view.findViewById(R.id.read_button)
        readBtn.setOnClickListener({
            secretLink?.let { presenter.readSecret(it) }
        })

        return view
    }

    override fun onReadError(message: String?) {
        val text = message ?: getString(R.string.operation_undefined_error)
        context.toast(text)
    }

    override fun onReadSuccess(value: String?) {
        secret.visibility = View.VISIBLE
        secret.text = value
        readBtn.visibility = View.GONE
    }
}