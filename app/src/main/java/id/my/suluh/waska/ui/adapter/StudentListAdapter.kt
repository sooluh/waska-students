package id.my.suluh.waska.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.my.suluh.waska.db.api.response.StudentList
import id.my.suluh.waska.databinding.StudentItemBinding
import id.my.suluh.waska.R
import java.net.URLEncoder

class StudentListAdapter(private val listStudents: ArrayList<StudentList>) :
    RecyclerView.Adapter<StudentListAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: StudentItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = StudentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val student = listStudents[position]

        holder.binding.apply {
            val encodedName = URLEncoder.encode(student.name, "UTF-8")

            cardTvName.text = student.name
            cardTvNumber.text = student.number.toString()

            Glide.with(root.context)
                .load("https://api.dicebear.com/8.x/notionists-neutral/png?size=256&seed=${encodedName}")
                .placeholder(R.drawable.ic_person_24)
                .into(profilePicture)
        }

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(student) }
    }

    override fun getItemCount(): Int = listStudents.size

    interface OnItemClickCallback {
        fun onItemClicked(student: StudentList)
    }

}
