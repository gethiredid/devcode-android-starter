package devcode.android.starter.modules.retrieve_data

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import devcode.android.starter.databinding.ContactListItemBinding
import devcode.android.starter.model.ContactItem
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timerTask

interface ContactAdapterInterface {
    fun onEditClick(contactItem: ContactItem)
    fun onDeleteClick(contactItem: ContactItem, index: Int)
}

class ContactAdapter(val context: Context, val contactAdapterInterface: ContactAdapterInterface) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    private var contacts = mutableListOf<ContactItem>()

    fun updateList(items: MutableList<ContactItem>) {
        contacts = items
        notifyDataSetChanged()
    }

    fun insertContact(item: ContactItem) {
        contacts.add(item)
        notifyDataSetChanged()
    }

    fun updateContact(item: ContactItem) {
        val selectedIndex = contacts.indexOfFirst { it.id == item.id }
        contacts[selectedIndex] = item
        notifyDataSetChanged()
    }

    fun removeContact(index: Int) {
        contacts.removeAt(index)
        notifyDataSetChanged()
    }

    inner class ContactViewHolder(private val binding: ContactListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val contact = contacts[position]

            binding.itemName.text = contact.fullName
            binding.itemPhone.text = contact.phoneNumber
            binding.itemEmail.text = contact.email

            binding.buttonEdit.contentDescription = "btn-edit-$position"
            binding.buttonDelete.contentDescription = "btn-delete-$position"

            binding.buttonEdit.setOnClickListener { contactAdapterInterface.onEditClick(contact) }
            binding.buttonDelete.setOnClickListener { contactAdapterInterface.onDeleteClick(contact, position) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ContactListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = contacts.size
    override fun getItemId(position: Int): Long = position.toLong()
}