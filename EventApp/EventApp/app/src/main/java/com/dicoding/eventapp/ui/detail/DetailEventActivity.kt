package com.dicoding.eventapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.dicoding.eventapp.data.api.ApiConfig
import com.dicoding.eventapp.data.repository.EventRepository
import com.dicoding.eventapp.databinding.ActivityDetailEventBinding
import com.dicoding.eventapp.ui.ViewModelFactory
import com.dicoding.eventapp.ui.adapter.EventAdapter

class DetailEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEventBinding
    private val viewModel: DetailEventViewModel by viewModels {
        ViewModelFactory(EventRepository(ApiConfig.apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventId = intent.getStringExtra(EventAdapter.EXTRA_EVENT_ID)
        eventId?.let { viewModel.getEventDetail(it) }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.event.observe(this) { event ->
            binding.apply {
                tvEventName.text = event.name
                tvOwnerName.text = event.ownerName
                tvBeginTime.text = event.beginTime
                tvQuota.text = "${event.quota - event.registrants} left"
                tvDescription.text = HtmlCompat.fromHtml(event.description, HtmlCompat.FROM_HTML_MODE_LEGACY)

                Glide.with(this@DetailEventActivity)
                    .load(event.mediaCover)
                    .into(ivEventCover)

                btnOpenLink.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
                    startActivity(intent)
                }
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}