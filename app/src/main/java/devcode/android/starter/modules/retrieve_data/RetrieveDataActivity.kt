package devcode.android.starter.modules.retrieve_data

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import devcode.android.starter.base.BaseActivity
import devcode.android.starter.databinding.ActivityRetrieveDataBinding
import devcode.android.starter.utils.RequestStatus
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class RetrieveDataActivity : BaseActivity() {

    private lateinit var binding: ActivityRetrieveDataBinding

    private val contactAdapter: ContactAdapter by inject { parametersOf(this) }
    private val retrieveDataViewModel: RetrieveDataViewModel by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRetrieveDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        observeData()

        requestApi()
    }

    private fun requestApi() {
        retrieveDataViewModel.getContactList()
    }

    private fun observeData() {
        retrieveDataViewModel.contactStatus.observe(this) { status ->
            if (status == RequestStatus.SUCCESS) {
                retrieveDataViewModel.contactData?.let { contactData ->
                    contactAdapter.updateList((contactData.data ?: listOf()).toMutableList())
                }
            }

            binding.progressBar.visibility = if (status == RequestStatus.LOADING) View.VISIBLE else View.GONE
        }

        retrieveDataViewModel.createContactStatus.observe(this) { status ->
            if (status == RequestStatus.SUCCESS) {
                retrieveDataViewModel.newContact?.let { contact ->
                    contactAdapter.insertContact(contact)
                }
            }
        }
    }

    private fun initView() {
        supportActionBar?.hide()

        binding.buttonSubmit.setOnClickListener { retrieveDataViewModel.createContact() }

        initRecyclerView()

        initForm()
    }

    private fun initForm() {
        binding.inputNama.doOnTextChanged { text, _, _, _ -> retrieveDataViewModel.fullname = text.toString() }
        binding.inputTelepon.doOnTextChanged { text, _, _, _ -> retrieveDataViewModel.phoneNumber = text.toString() }
        binding.inputEmail.doOnTextChanged { text, _, _, _ -> retrieveDataViewModel.email = text.toString() }
    }

    private fun initRecyclerView() {
        binding.rvContact.adapter = contactAdapter
        binding.rvContact.layoutManager = LinearLayoutManager(this)
    }
}