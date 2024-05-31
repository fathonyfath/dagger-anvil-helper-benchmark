package dev.fathony.anvilhelper.dagger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dev.fathony.anvilhelper.base.dagger.DaggerComponent
import dev.fathony.anvilhelper.base.dagger.DaggerComponentOwner
import dev.fathony.anvilhelper.base.dagger.applicationComponent
import dev.fathony.anvilhelper.base.page.IntentBuilder
import dev.fathony.anvilhelper.base.page.Page
import dev.fathony.anvilhelper.base.page.PageGroup
import dev.fathony.anvilhelper.dagger.databinding.ActivityMainBinding
import dev.fathony.anvilhelper.dagger.di.MainActivityComponentFactory

class MainActivity : AppCompatActivity(), ActivityLauncher, DaggerComponentOwner {

    override val component: DaggerComponent<MainActivity>
            by applicationComponent { component: MainActivityComponentFactory ->
                component.createMainActivityComponent(this)
            }

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
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
