package dev.fathony.anvilhelper.dagger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dev.fathony.anvilhelper.base.IntentBuilder
import dev.fathony.anvilhelper.base.Page
import dev.fathony.anvilhelper.base.PageGroup
import dev.fathony.anvilhelper.dagger.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ActivityLauncher {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mainAdapter = MainAdapter(
            this,
            this,
            listOf(
                MainItem.Header(PageGroup("Sample-1")),
                MainItem.Item(Page("Test", object : IntentBuilder() {
                    override fun create(context: Context): Intent {
                        return Intent(context, MainActivity::class.java)
                    }
                }))
            )
        )

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mainAdapter
        }
    }

    override fun launchActivity(intent: Intent) {
        startActivity(intent)
    }
}
