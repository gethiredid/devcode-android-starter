package devcode.android.starter.modules.retrieve_data

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import devcode.android.starter.base.BaseActivity
import devcode.android.starter.databinding.ActivityRetrieveDataBinding
import devcode.android.starter.databinding.SuccessCreateEditContactDialogBinding
import devcode.android.starter.model.ContactItem
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
                resetInput()

                retrieveDataViewModel.newContact?.let { contact ->
                    contactAdapter.insertContact(contact)
                    showCreateEditResultDialog(contact)
                }
            }
        }

        retrieveDataViewModel.createEditContactResult.observe(this) { status ->
            if (status == CreateEditContactResult.DUPLICATE) {
                showCreateEditResultDialog(ContactItem(email = retrieveDataViewModel.email, fullName = retrieveDataViewModel.fullname, phoneNumber = retrieveDataViewModel.phoneNumber))
            }
        }
    }

    private fun resetInput() {
        binding.inputNama.text.clear()
        binding.inputTelepon.text.clear()
        binding.inputEmail.text.clear()

        retrieveDataViewModel.fullname = ""
        retrieveDataViewModel.phoneNumber = ""
        retrieveDataViewModel.email = ""
    }

    private fun initView() {
        supportActionBar?.hide()

        binding.buttonSubmit.setOnClickListener {
            // TODO: Uncomment code di bawah untuk memanggil function [createContact] yang berada
            //  di class [RetrieveDataViewModel] menggunakan variabel [retrieveDataViewModel] yang
            //  sudah di define sebelumnya.

            // retrieveDataViewModel.createContact()
        }

        initRecyclerView()

        initForm()
    }

    private fun initForm() {
        binding.inputNama.doOnTextChanged { text, _, _, _ -> retrieveDataViewModel.fullname = text.toString() }
        binding.inputTelepon.doOnTextChanged { text, _, _, _ -> retrieveDataViewModel.phoneNumber = text.toString() }

        // TODO: Tulis code untuk mendapatkan value dari [inputEmail] seperti pada code di atas.
        // TODO: Ubah variabel [email] yang berada pada class RetrieveDataViewModel dengan cara seperti code di atas.
    }

    private fun initRecyclerView() {
        binding.rvContact.adapter = contactAdapter
        binding.rvContact.layoutManager = LinearLayoutManager(this)
    }

    private fun showCreateEditResultDialog(contactItem: ContactItem) {
        val dialog = Dialog(this)
        val binding = SuccessCreateEditContactDialogBinding.inflate(LayoutInflater.from(this))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT), 48))

        if (retrieveDataViewModel.createEditContactResult.value == CreateEditContactResult.DUPLICATE) {
            binding.deleteTitle.text = "Data yang anda masukkan sudah terdaftar di server!"
        }

        if (retrieveDataViewModel.createContactStatus.value == RequestStatus.SUCCESS) {
            binding.deleteTitle.text = "Berhasil memasukkan data ke server!"
        }

        binding.fullname.text = contactItem.fullName
        binding.phone.text = contactItem.phoneNumber
        binding.email.text = contactItem.email

        binding.cancelButton.setOnClickListener {
            dialog.dismiss()
            resetInput()
        }

        dialog.show()
    }
}