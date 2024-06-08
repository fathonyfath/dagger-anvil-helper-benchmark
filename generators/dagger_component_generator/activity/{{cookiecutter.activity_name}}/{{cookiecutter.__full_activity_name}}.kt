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
import dev.fathony.anvilhelper.base.dagger.DaggerComponent
import dev.fathony.anvilhelper.base.dagger.DaggerComponentOwner
import dev.fathony.anvilhelper.base.dagger.applicationComponent
import dev.fathony.anvilhelper.base.page.IntentBuilder
import dev.fathony.anvilhelper.common.NumberProvider
import javax.inject.Inject
import javax.inject.Singleton

class {{ cookiecutter.__full_activity_name }} : AppCompatActivity(), DaggerComponentOwner {

    override val component: DaggerComponent<{{ cookiecutter.__full_activity_name }}>
            by applicationComponent { component: {{ cookiecutter.__full_activity_component_factory_name}} ->
                component.{{ cookiecutter.__activity_component_factory_create_function_name}}(this)
            }

    @Inject
    lateinit var numberProvider: NumberProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
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
