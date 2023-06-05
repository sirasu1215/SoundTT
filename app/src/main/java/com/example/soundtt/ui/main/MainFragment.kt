package com.example.soundtt.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.soundtt.AccEstimation
import com.example.soundtt.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var logHit: TextView
    private lateinit var logSwing: TextView
    private lateinit var logCount:TextView
    private lateinit var logDb:TextView
    //val accEstimation = AccEstimation()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val rootView = inflater.inflate(R.layout.fragment_main, container, false)

        logHit = rootView.findViewById<TextView>(R.id.textView_hit)
        logSwing = rootView.findViewById<TextView>(R.id.textView_swing)
        logCount = rootView.findViewById<TextView>(R.id.textView_count)
        logDb = rootView.findViewById<TextView>(R.id.textView_db)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // これ絶対最初
        viewModel.start(requireContext())

        //EstimationのaccTestの値が変わった瞬間テキスト表示
        viewModel.accSensor.accEstimation.accTest.observe(viewLifecycleOwner){
            Log.d("MainFragment" ,it)
            logHit.text = it
        }

        // applicationのLiveDataを監視してヒットした瞬間
        viewModel.accSensor.accEstimation.isHit.observe(viewLifecycleOwner){
            Log.d("MainFragment", "hit? = $it")
            if (it){
                logHit.text = "Hit"

                /*
                val swing = viewModel.accSensor.estimation._isSwing.value
                Log.d("MainFragment","Fragment_Swing? = $swing")
                if (swing == true){
                    val rallyCount = logCount.text
                    val count = rallyCount.toString().toInt()+1
                    logCount.text = count.toString()
                    viewModel.accSensor.estimation._isSwing.postValue(false)
                    viewModel.accSensor.estimation._isHit.postValue(false)
                    viewModel.accSensor.estimation.bl_onhit = false
                    viewModel.accSensor.estimation.bl_onswing = false
                }

                 */
            }else{
                logHit.text = "No Hit"
            }

        }

        viewModel.accSensor.accEstimation.isSwing.observe(viewLifecycleOwner){
            Log.d("MainFragment", "swing? = $it")

            if (it){
                logSwing.text = "Swing"

                val hit = viewModel.accSensor.accEstimation._isHit.value
                Log.d("MainFragment","Fragment_Hit? = $hit")
                if (hit == true){
                    val rallyCount = logCount.text
                    val count = rallyCount.toString().toInt()+1
                    logCount.text = count.toString()
                    viewModel.accSensor.accEstimation._isSwing.postValue(false)
                    viewModel.accSensor.accEstimation._isHit.postValue(false)
                    viewModel.accSensor.accEstimation.bl_onhit = false
                    viewModel.accSensor.accEstimation.bl_onswing = false

                    var volume = viewModel.audioSensor.getVolume()
                    logDb.text = volume.toString()

                }

            }else{
                logSwing.text = "No Swing"
            }

        }
        /*
        viewModel.audioSensor.audioEstimation.isVolume.observe(viewLifecycleOwner){
            Log.d("MainFragment","Frangment_Db? = $it")
            logDb.text = it.toString()
        }

         */
    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.stop()

    }

}