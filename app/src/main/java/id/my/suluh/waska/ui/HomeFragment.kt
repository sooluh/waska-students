package id.my.suluh.waska.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import id.my.suluh.waska.viewmodel.HomeViewModel
import id.my.suluh.waska.databinding.FragmentHomeBinding
import id.my.suluh.waska.db.api.response.StudentList
import id.my.suluh.waska.ui.adapter.StudentListAdapter

class HomeFragment : Fragment() {
    private var lastClick: Long = 0
    private val clickDelay = 500

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)

            searchView.editText.setOnEditorActionListener { _, _, _ ->
                val searchText = searchView.text.toString()
                val keywords = if (searchText == "") null else searchText

                searchBar.setText(keywords)
                searchView.hide()
                homeViewModel.searchStudent(keywords)
                false
            }
        }

        homeViewModel.studentList.observe(viewLifecycleOwner) { students ->
            if (students == null) {
                homeViewModel.searchStudent(null)
            } else {
                showStudents(students)
            }
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        homeViewModel.isError.observe(viewLifecycleOwner) { error ->
            if (error) errorOccurred();
        }

        return binding.root
    }

    private fun showStudents(students: ArrayList<StudentList>) {
        if (students.isEmpty()) {
            with (binding) {
                rvStudents.visibility = View.GONE
                noStudentsFound.visibility = View.VISIBLE
            }

            return
        }

        val linearLayoutManager = LinearLayoutManager(activity)
        val listAdapter = StudentListAdapter(students)

        with (binding) {
            rvStudents.apply {
                layoutManager = linearLayoutManager
                adapter = listAdapter
                setHasFixedSize(true)
            }
            rvStudents.visibility = View.VISIBLE
        }

        listAdapter.setOnItemClickCallback(object : StudentListAdapter.OnItemClickCallback {
            override fun onItemClicked(student: StudentList) {
                val clickTime = System.currentTimeMillis()

                if (clickTime - lastClick > clickDelay) {
                    navigateToStudentDetail(student.number)
                }
            }
        })
    }

    private fun errorOccurred() {
        Toast.makeText(activity, "An error occurred", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) pbLoading.visibility = View.VISIBLE
            else pbLoading.visibility = View.GONE
        }
    }

    private fun navigateToStudentDetail(number: Int) {
        Intent(activity, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_NUMBER, number)
        }.also {
            startActivity(it)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}
