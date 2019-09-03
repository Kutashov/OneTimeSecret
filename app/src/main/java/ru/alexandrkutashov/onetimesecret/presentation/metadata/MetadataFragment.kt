package ru.alexandrkutashov.onetimesecret.presentation.metadata

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.alexandrkutashov.onetimesecret.R
import ru.alexandrkutashov.onetimesecret.ext.OTPLink.copyLink
import ru.alexandrkutashov.onetimesecret.presentation.FabListener
import ru.alexandrkutashov.onetimesecret.presentation.base.AppFragment
import ru.alexandrkutashov.onetimesecret.presentation.share.ShareFragment

/**
 * @author Alexandr Kutashov
 * on 08.04.2018
 */

class MetadataFragment : AppFragment(), MetadataView, FabListener {

    @InjectPresenter
    lateinit var presenter: MetadataPresenter

    companion object {
        private const val METADATA_KEY = "metadataKey"
        val screenKey: String = MetadataFragment::class.simpleName!!
        fun newInstance(metadataKey: String?): Fragment {
            val fragment = MetadataFragment()
            metadataKey?.let {
                val bundle = Bundle()
                bundle.putString(METADATA_KEY, it)
                fragment.arguments = bundle
            }
            return fragment
        }
    }

    private lateinit var link: TextView
    private lateinit var passwordRequired: TextView
    private lateinit var timeLeft: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.getSecretMetadata(arguments?.getString(METADATA_KEY))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_metadata, container, false)

        link = view.findViewById(R.id.secret_link)
        passwordRequired = view.findViewById(R.id.password_required)
        timeLeft = view.findViewById(R.id.time_left)

        view.findViewById<Button>(R.id.burn_secret_btn).setOnClickListener {
            AlertDialog.Builder(requireContext())
                    .setTitle(R.string.burn_dialog_title)
                    .setMessage(R.string.burn_dialog_message)
                    .setPositiveButton(android.R.string.yes) { _, _ ->
                        presenter.burnSecret(arguments?.getString(METADATA_KEY))
                    }
                    .setNegativeButton(android.R.string.no) { dialog, _ -> dialog.dismiss() }
                    .create().show()
        }

        showFab()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideFab()
    }

    override fun onBurnSecretSuccess() {
        router.showSystemMessage(getString(R.string.burn_secret_success))
        router.newRootScreen(ShareFragment.screenKey)
    }

    override fun onError(message: String?) {
        val text = message ?: getString(R.string.operation_undefined_error)
        Snackbar.make(passwordRequired, text, Snackbar.LENGTH_LONG).show()
    }

    override fun onMetadataSuccess(secretLink: String?, isPassphraseRequired: Boolean, inTimeString: String?) {
        link.text = secretLink
        if (isPassphraseRequired) {
            passwordRequired.visibility = View.VISIBLE
        }
        inTimeString?.let { timeLeft }?.apply {
            text = inTimeString
            visibility = View.VISIBLE
        }
    }

    override fun onClick(view: View) {
        val link = link.text
        if (link.isNotEmpty()) {
            copyLink(requireContext(), link.toString())
            router.showSystemMessage(getString(R.string.link_copied))
        }
    }
}