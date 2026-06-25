package ma.enset.imc

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainLayout = findViewById<android.widget.LinearLayout>(R.id.mainLayout)
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(view.paddingLeft, insets.top, view.paddingRight, view.paddingBottom)
            windowInsets
        }

        val editTextWeight = findViewById<EditText>(R.id.editTextWeight)
        val editTextHeight = findViewById<EditText>(R.id.editTextHeight)
        val btnCompute = findViewById<Button>(R.id.btnCompute)
        val textViewResult = findViewById<TextView>(R.id.textViewResult)
        val imageViewStatus = findViewById<ImageView>(R.id.imageViewStatus)
        val listViewResult = findViewById<ListView>(R.id.listViewResults)

        val data = ArrayList<String>()
        val stringArrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        listViewResult.adapter = stringArrayAdapter

        btnCompute.setOnClickListener {
            val weightStr = editTextWeight.text.toString()
            val heightStr = editTextHeight.text.toString()

            if (weightStr.isEmpty() || heightStr.isEmpty()) return@setOnClickListener

            val weight = weightStr.toDouble()
            val height = heightStr.toDouble()

            if (height <= 0.0) return@setOnClickListener

            val imc = weight / (height * height)
            val formattedImc = String.format("%.2f", imc)
            textViewResult.text = "Votre IMC: $formattedImc"

            val (imageRes, statusText) = when {
                imc < 18.5 -> Pair(R.drawable.maigre, "Maigre")
                imc in 18.5..24.9 -> Pair(R.drawable.normal, "Normal")
                imc in 25.0..29.9 -> Pair(R.drawable.surpoids, "Surpoids")
                imc in 30.0..34.9 -> Pair(R.drawable.obese, "Obèse")
                else -> Pair(R.drawable.t_obese, "Très Obèse")
            }

            imageViewStatus.setImageResource(imageRes)
            imageViewStatus.visibility = View.VISIBLE

            data.add(0, "P:$weight kg | T:$height m -> IMC: $formattedImc ($statusText)")
            stringArrayAdapter.notifyDataSetChanged()

            editTextWeight.setText("")
            editTextHeight.setText("")
        }
    }
}