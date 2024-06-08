package dev.fathony.anvilhelper.anvil

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dev.fathony.anvil.helper.api.DefineEntryPoint
import dev.fathony.anvilhelper.base.anvil.ApplicationScope
import dev.fathony.anvilhelper.base.dagger.AndroidInjection
import dev.fathony.anvilhelper.base.dagger.scope.ActivityScope
import dev.fathony.anvilhelper.base.page.PageGroup
import dev.fathony.anvilhelper.anvil.databinding.ActivityMainBinding
import dev.fathony.anvilhelper.anvil.di.MainActivityScope
import javax.inject.Inject

@ActivityScope
@DefineEntryPoint(MainActivityScope::class, ApplicationScope::class)
class MainActivity : AppCompatActivity(), ActivityLauncher {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var pageGroups: Set<PageGroup>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
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
