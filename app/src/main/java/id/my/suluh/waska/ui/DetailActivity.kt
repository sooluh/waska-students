package id.my.suluh.waska.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.bumptech.glide.Glide
import id.my.suluh.waska.R
import id.my.suluh.waska.databinding.ActivityDetailBinding
import id.my.suluh.waska.db.api.response.StudentDetail
import id.my.suluh.waska.viewmodel.DetailViewModel
import java.net.URLEncoder

class DetailActivity : AppCompatActivity() {
    private var lastClick: Long = 0
    private val clickDelay = 500

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private var number: Int? = null
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailBinding.inflate(layoutInflater)
        number = intent.getIntExtra(EXTRA_NUMBER, 0)

        setContentView(binding.root)

        detailViewModel.getDetail(number!!)

        detailViewModel.student.observe(this) { student ->
            if (student != null) {
                parseDetail(student)
            }
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.isError.observe(this) { error ->
            if (error) errorOccurred()
        }
    }

    private fun parseDetail(student: StudentDetail) {
        binding.apply {
            val encodedName = URLEncoder.encode(student.name, "UTF-8")

            tvName.text = student.name
            tvNumber.text = student.number.toString()
            tvGender.text = if (student.gender == 'M') "Laki-laki" else "Perempuan"
            tvAdmissionYear.text = student.admissionYear.toString()
            tvFirstSemester.text = if (student.firstSemester == 1) "Ganjil" else "Genap"
            tvIsGraduated.text = if (student.isGraduated) "Lulus" else "Belum lulus"
            tvEducationLevel.text = student.educationLevel
            tvStudy.text = student.study

            Glide.with(root.context)
                .load("https://api.dicebear.com/8.x/notionists-neutral/png?size=256&seed=${encodedName}")
                .placeholder(R.drawable.ic_person_24)
                .into(ivProfilePicture)

            btnPddikti.setOnClickListener {
                val clickTime = System.currentTimeMillis()

                if (clickTime - lastClick > clickDelay) {
                    val builder = CustomTabsIntent.Builder()

                    builder.setShowTitle(true)
                    builder.setShareState(CustomTabsIntent.SHARE_STATE_ON)
                    builder.setInstantAppsEnabled(true)

                    val customBuilder = builder.build()

                    try {
                        customBuilder.launchUrl(this@DetailActivity, Uri.parse(student.pddikti))
                    } catch (e: ActivityNotFoundException) {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(student.pddikti))
                        startActivity(browserIntent)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        number = null

        super.onDestroy()
    }

    private fun errorOccurred() {
        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) pbLoading.visibility = View.VISIBLE
            else pbLoading.visibility = View.GONE
        }
    }

    fun onBackPressed(view: View) {
        super.onBackPressedDispatcher.onBackPressed()
    }

    companion object {
        const val EXTRA_NUMBER = "extra_number"
    }
}
