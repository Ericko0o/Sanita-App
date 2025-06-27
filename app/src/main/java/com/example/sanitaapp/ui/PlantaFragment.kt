package com.example.sanitaapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sanitaapp.R
import com.example.sanitaapp.api.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlantaFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_planta, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recyclerPlantas)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val respuesta = ApiClient.apiService.getPlantas()
                withContext(Dispatchers.Main) {
                    recyclerView.adapter = PlantaAdapter(respuesta)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error al cargar plantas", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}