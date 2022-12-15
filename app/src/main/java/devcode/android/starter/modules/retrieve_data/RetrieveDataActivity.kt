package devcode.android.starter.modules.retrieve_data

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import devcode.android.starter.base.BaseActivity
import devcode.android.starter.databinding.ActivityRetrieveDataBinding
import devcode.android.starter.utils.RequestStatus
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class RetrieveDataActivity : BaseActivity() {

    private lateinit var binding: ActivityRetrieveDataBinding

    private val contactAdapter: ContactAdapter by inject { parametersOf(this) }
    private val detailGroupViewModel: RetrieveDataViewModel by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRetrieveDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        observeData()

        requestApi()
    }

    private fun requestApi() {
        detailGroupViewModel.getContactList()
    }

    private fun observeData() {
        detailGroupViewModel.contactStatus.observe(this) { status ->
            if (status == RequestStatus.SUCCESS) {
                detailGroupViewModel.contactData?.let { contactData ->
                    contactAdapter.updateList((contactData.data ?: listOf()).toMutableList())
                }
            }

            binding.progressBar.visibility = if (status == RequestStatus.LOADING) View.VISIBLE else View.GONE
        }
    }

    private fun initView() {
        supportActionBar?.hide()

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvContact.adapter = contactAdapter
        binding.rvContact.layoutManager = LinearLayoutManager(this)
    }
}