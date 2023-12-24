package com.example.retailstoreanalysissystem

import android.view.LayoutInflater
import android.os.Bundle
import android.view.*
//import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retailstoreanalysissystem.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var productNameEditText: EditText
    private lateinit var productPriceEditText: EditText
    private lateinit var addProductButton: Button
    private lateinit var productRecyclerView: RecyclerView

    private val productList = mutableListOf<Product>()
    private lateinit var productAdapter: ProductAdapter

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        /*binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        productNameEditText = findViewById(R.id.productNameEditText)
        productPriceEditText = findViewById(R.id.productPriceEditText)
        addProductButton = findViewById(R.id.addProductButton)
        productRecyclerView = findViewById(R.id.productRecyclerView)

        // Initialize RecyclerView and Adapter
        productAdapter = ProductAdapter(productList)
        productRecyclerView.layoutManager = LinearLayoutManager(this)
        productRecyclerView.adapter = productAdapter

        addProductButton.setOnClickListener {
            // Handle the logic to add a product
            val productName = productNameEditText.text.toString()
            val productPrice = productPriceEditText.text.toString().toDoubleOrNull()

            if (productName.isNotEmpty() && productPrice != null) {
                val newProduct = Product(productName, productPrice)
                productList.add(newProduct)
                productAdapter.notifyDataSetChanged()

            }
        }
    }

    // Data class representing a product
    data class Product(val name: String, val price: Double)

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    // RecyclerView Adapter for displaying products
    class ProductAdapter(private val productList: List<Product>) :
        RecyclerView.Adapter<ProductViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product, parent, false)
            return ProductViewHolder(view)
        }

        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
            val product = productList[position]
            holder.bind(product)
        }

        override fun getItemCount(): Int {
            return productList.size
        }
    }

    // ViewHolder for individual product items
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productNameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
        private val productPriceTextView: TextView = itemView.findViewById(R.id.productPriceTextView)

        fun bind(product: Product) {
            productNameTextView.text = product.name
            productPriceTextView.text = "$${product.price}"
        }
    }

}