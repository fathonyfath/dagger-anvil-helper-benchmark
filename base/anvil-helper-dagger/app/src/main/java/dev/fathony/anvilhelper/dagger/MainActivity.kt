package dev.fathony.anvilhelper.dagger

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dev.fathony.anvilhelper.base.dagger.DaggerComponent
import dev.fathony.anvilhelper.base.dagger.DaggerComponentOwner
import dev.fathony.anvilhelper.base.dagger.applicationComponent
import dev.fathony.anvilhelper.base.page.PageGroup
import dev.fathony.anvilhelper.dagger.databinding.ActivityMainBinding
import dev.fathony.anvilhelper.dagger.di.MainActivityComponentFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ActivityLauncher, DaggerComponentOwner {

    override val component: DaggerComponent<MainActivity>
            by applicationComponent { component: MainActivityComponentFactory ->
                component.createMainActivityComponent(this)
            }

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var pageGroups: Set<PageGroup>

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

        val items = pageGroups
            .sortedBy { it.name }
            .flatMap { item ->
                return@flatMap listOf(MainItem.Header(item)) + item.pages.map { MainItem.Item(it) }
            }

        val mainAdapter = MainAdapter(
            context = this,
            activityLauncher = this,
            items = items
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
