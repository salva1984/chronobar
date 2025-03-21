package ok.slvdr.chronobar

import android.app.Activity
import android.os.Bundle
import ok.slvdr.chronobar.databinding.MainWidgetConfigureBinding

/**
 * The configuration screen for the [MainWidget] AppWidget.
 */
class MainWidgetConfigureActivity : Activity() {
    private lateinit var binding: MainWidgetConfigureBinding
    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        binding = MainWidgetConfigureBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}

