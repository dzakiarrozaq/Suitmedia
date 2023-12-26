package com.bangkit.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.suitmedia.databinding.ListPersonBinding
import com.bangkit.suitmedia.response.DataItem
import com.bumptech.glide.Glide

class UserAdapter(): RecyclerView.Adapter<UserAdapter.ItemViewHolder>() {

    private val userList = ArrayList<DataItem>()
    private var onClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = ListPersonBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  ItemViewHolder((view))
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    inner class ItemViewHolder(private val itemBinding: ListPersonBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(data: DataItem) {
            itemBinding.root.setOnClickListener{
                onClickCallback?.onItemClicked(data)
            }
            itemBinding.apply {
                tvFirstName.text = data.firstName
                tvLastName.text = data.lastName
                tvEmail.text = data.email
                Glide.with(itemView)
                    .load(data.avatar)
                    .circleCrop()
                    .into(userPhoto)
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: DataItem)
    }

    fun setClickCallback(ItemClickCallback: OnItemClickCallback){
        this.onClickCallback = ItemClickCallback
    }
    fun clearUsers() {
        this.userList.clear()
        notifyDataSetChanged()
    }

    fun setList(users:ArrayList<DataItem>){
        userList.clear()
        userList.addAll(users)
        notifyDataSetChanged()
    }
}
