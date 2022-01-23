package org.techtown.plogging_android.Plogging

import android.location.Location
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.FusedLocationSource
import org.techtown.plogging_android.R
import org.techtown.plogging_android.databinding.FragmentPloggingBinding
import kotlin.math.roundToInt


class PloggingFragment : Fragment(),OnMapReadyCallback {

    lateinit var binding : FragmentPloggingBinding

    private lateinit var locationSource: FusedLocationSource    //최적의 위치를 반환하는 구현체
    private lateinit var naverMap: NaverMap

    private lateinit var path: PathOverlay
    private val pathArray: ArrayList<LatLng> = arrayListOf()

    private lateinit var walk: Walk

    private var isWalking: Boolean = true
    private var currentTime: Int = 0

    private var totalDistance: Float = 0.0f
    private lateinit var lastLocation: Location

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPloggingBinding.inflate(inflater , container , false)
        setListener()
        setInit()

        return binding.root
    }

    fun setListener(){

        binding.ploggingStartBtn.setOnClickListener {
            binding.ploggingStartBtn.visibility = View.GONE
            binding.btnContainer.visibility = View.VISIBLE
            setWalkState(true)
            walk = Walk()
            walk.start()
        }

        binding.pauseContent.setOnClickListener {
            setWalkState(false)
            binding.pauseContent.visibility = View.GONE
            binding.playContent.visibility = View.VISIBLE
        }

        binding.playContent.setOnClickListener {
            setWalkState(true)
            binding.pauseContent.visibility = View.VISIBLE
            binding.playContent.visibility = View.GONE
        }

        binding.stopCircle.setOnClickListener {
            binding.ploggingStartBtn.visibility = View.VISIBLE
            binding.btnContainer.visibility = View.GONE
            stopWalk()

        }
    }


    fun setInit(){
        //내 위치로 이동하는 버튼 생기게하기 ?
        val options = NaverMapOptions()
            .locationButtonEnabled(true)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance(options).also {
                childFragmentManager.beginTransaction().add(R.id.map, it).commit()
            }

        //mapFragment는 지도에 대한 뷰 역할만을 담당 ,API를 호출하려면 인터페이스 역할을 하는  NaverMap객체가 필요합니다.
        //NaverMap 객체가 준비되면 onMapReady 호출
        mapFragment.getMapAsync(this)

        //locationSource 정의 , 권한처리를 위해 액티비티나 프래그먼트 필요 ->this로 지정
        //위치 추적 모드를 Face로 설정 ->위치 추적이 활성화되고, 현위치 오버레이, 카메라의 좌표, 베어링이 사용자의 위치 및 방향을 따라 움직입니다. API나 제스처를 사용해 임의로 카메라를 움직일 경우 모드가 NoFollow로 바뀝니다.
        locationSource = FusedLocationSource(requireActivity(), LOCATION_PERMISSION_REQUEST_CODE)

    }

    override fun onMapReady(naverMap: NaverMap) {
        //내 위치 추적 구현하기

        //내 위치로 이동하기 버튼 활성화 -> XML에서 가능
        this.naverMap = naverMap
        setMap()

    }

    fun setMap(){
        naverMap.locationSource = locationSource
        naverMap.moveCamera(CameraUpdate.zoomTo(15.0))
        naverMap.uiSettings.isZoomControlEnabled = false
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        //위치 오버레이 띄우기 구현
        val locationOverlay = naverMap.locationOverlay
        locationOverlay.isVisible = true

        //경로 오버레이 효과 셋팅
        path = PathOverlay()
        path.apply {
            width = 20
            color = ContextCompat.getColor(requireContext(), R.color.main_color)
            outlineWidth = 0
        }

        //지도 옵션 변경에 대한 이벤트 리스너 인터페이스. 지도의 최소/최대 줌, 레이어 활성화/비활성화 상태 등이 변경되면 이벤트가 발생합니다.
        naverMap.addOnOptionChangeListener {
            val mode = naverMap.locationTrackingMode

            if (mode == LocationTrackingMode.NoFollow) {
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
                locationOverlay.subIcon = null
            }

            if (mode == LocationTrackingMode.Follow) {
                locationOverlay.subIcon = null
            }
        }

        naverMap.addOnLocationChangeListener {location->
            drawPath(location)
        }


    }

    fun drawPath(location: Location){
        //pathArray에 현재 위치 저장하는 코드
        pathArray.add(LatLng(location.latitude , location.longitude))

        if (pathArray.size >= 2) {
            updateDistance(location)

            updateCalorie()

            path.coords = pathArray         //coords 크기가 2 미만이면 에러이므로 if 문 >= 2 조건 존재
            path.map = naverMap
        }

    }

    //칼로리 업데이트
    private fun updateCalorie() {
        val calroieConstant = 0.0525 * 50

        binding.ploggingKcalNum.text =
            (calroieConstant * (currentTime / 60)).roundToInt().toString()

    }

    //거리 업데잍
    private fun updateDistance(location: Location) {
        if (!::lastLocation.isInitialized) {            //lastLocation이 초기화 되어있지 않으면 -> 초기화
            lastLocation = location
            return
        }

        totalDistance += location.distanceTo(lastLocation)
        binding.ploggingDistanceNum.text =
            String.format("%.1f", totalDistance / 1000)

        lastLocation = location
    }


    private fun stopWalk() {
        if (isWalking) {
            setWalkState(false)
        }

        if (::path.isInitialized && pathArray.size >= 2) {
            path.patternImage = null
            path.map = naverMap
        }


        //캡처해서 넘기기
        naverMap.takeSnapshot { bitmap ->
            Log.d("snapShot" ,"${bitmap} 촬영")

            requireActivity().supportFragmentManager.beginTransaction().
            replace(R.id.main_container_fl , CompletePloggingFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("snapshot" , bitmap)
                    putString("cal", binding.completeKcalNum.text.toString())
                    putString("dis" , binding.completeDistanceNum.text.toString())
                    putString("time",binding.completeTimeNum.text.toString())
                }
            })
                .addToBackStack(null).commitAllowingStateLoss()

        }
        walk.interrupt()
    }

    private fun setWalkState(isWalking: Boolean) {
        if (isWalking) {
            this.isWalking = true

            naverMap.locationTrackingMode = LocationTrackingMode.Follow
        } else {
            this.isWalking = false

            naverMap.locationTrackingMode = LocationTrackingMode.None
        }
    }


    //위치 권한 받는 코드
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
                grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }


    //걷는동안 시간 갱신해주는 Thread
    private inner class Walk() : Thread() {
        private lateinit var spannable : SpannableString
        private val spanColor = ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.black))

        override fun run() {
            super.run()
            try {
                while (true) {
                    if (isWalking) {
                        sleep(1000)
                        currentTime++

                        if (currentTime < 3600) {
                            spannable = SpannableString(String.format("%02d:%02d", currentTime / 60, currentTime % 60))
                        } else {
                            spannable =
                                SpannableString(String.format("%02d:%02d:%02d", currentTime / 3600, currentTime % 3600 / 60, currentTime % 3600 % 60))
                        }

                        setSpannableString()

                        requireActivity().runOnUiThread {
                            binding.ploggingTimeNum.text = spannable
                        }
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("interrupt","스레드가 종료되었습니다.")
            }

        }

        fun setSpannableString() {
            if (currentTime < 60) {
                spannable.setSpan(spanColor, 4, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            } else {
                spannable.setSpan(spanColor, 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 초기화 되었는지 확인하는 방법
        // ::변수명.isInitialized
        if(::walk.isInitialized){
            walk.interrupt()
        }

    }

}