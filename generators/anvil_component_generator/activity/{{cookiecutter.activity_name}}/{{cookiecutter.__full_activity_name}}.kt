package {{ cookiecutter.__full_package_name }}

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.fathony.anvil.helper.api.DefineEntryPoint
import dev.fathony.anvilhelper.base.anvil.ApplicationScope
import dev.fathony.anvilhelper.base.dagger.AndroidInjection
import dev.fathony.anvilhelper.base.dagger.scope.ActivityScope
import dev.fathony.anvilhelper.base.page.IntentBuilder
import dev.fathony.anvilhelper.common.NumberProvider
import javax.inject.Inject
import javax.inject.Singleton

@ActivityScope
@DefineEntryPoint({{ cookiecutter.__full_activity_scope_name }}::class, ApplicationScope::class)
class {{ cookiecutter.__full_activity_name }} : AppCompatActivity() {

    @Inject
    lateinit var numberProvider: NumberProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView({{ cookiecutter.__full_activity_layout_name }})
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        @SuppressLint("SetTextI18n")
        findViewById<TextView>(R.id.text)?.text = "Number: ${numberProvider.provideNumber()}"
    }

    @Singleton
    class Builder @Inject constructor() : IntentBuilder() {
        override fun create(context: Context): Intent {
            return Intent(context, {{ cookiecutter.__full_activity_name }}::class.java)
        }

    }
}
