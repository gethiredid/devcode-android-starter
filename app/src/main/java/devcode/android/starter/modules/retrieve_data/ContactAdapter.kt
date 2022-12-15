package devcode.android.starter.modules.retrieve_data

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import devcode.android.starter.databinding.ContactListItemBinding
import devcode.android.starter.model.ContactItem

class ContactAdapter(val context: Context) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    private var contacts = mutableListOf<ContactItem>()

    fun updateList(items: MutableList<ContactItem>) {
        contacts = items
        notifyDataSetChanged()
    }

    inner class ContactViewHolder(private val binding: ContactListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val contact = contacts[position]

            binding.itemName.text = contact.fullName
            binding.itemPhone.text = contact.phoneNumber
            binding.itemEmail.text = contact.email
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
    override fun getItemId(position: Int): Long = (contacts[position].id ?: 0).toLong()
}