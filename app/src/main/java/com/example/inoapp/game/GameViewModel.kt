package com.example.inoapp.game

import android.location.Location
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.inoapp.database.Point
import com.example.inoapp.database.Trip
import com.example.inoapp.database.TripDatabaseDao
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class GameViewModel(
    private val tripId: Long = 0L,
    private val currentIndex: Int = 0,
    dataSource: TripDatabaseDao) : ViewModel() {

    /** Hold a reference to InoDatabase via its TripDatabaseDao. */
    val database = dataSource

    /** Coroutine setup variables */
    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val trip: LiveData<Trip>
        fun getTrip() = trip


    private val points: LiveData<List<Point>>
        fun getPoints() = points

    private val _distanceToNextPoint = MutableLiveData<Float>()
    val distanceToNextPoint: LiveData<Float>
        get() = _distanceToNextPoint


    private val _currentPointIndex = MutableLiveData<Int>()
    val currentPointIndex: LiveData<Int>
        get() = _currentPointIndex

    private val _selectedTripId = MutableLiveData<Long>()
    val selectedTripId: LiveData<Long>
        get() = _selectedTripId

    private val _infoVisible = MutableLiveData<Int>()
    val infoVisible: LiveData<Int>
        get() = _infoVisible

    init {
        trip = database.getTripById(tripId)
        points = database.getPointsById(tripId)
        _currentPointIndex.value = currentIndex
        _selectedTripId.value = tripId
        _infoVisible.value = View.VISIBLE
        Log.d("GameViewModel", "GameViewModel created!") // todo: delete if not needed
    }


    /** onClick() method for ToggleViewButton */
    fun onToggleView() {
        Log.d("GameViewModel", "onToggleView clicked") // todo: delete if not needed
        _infoVisible.value = if (_infoVisible.value == View.VISIBLE) View.GONE
                             else View.VISIBLE
        /*_currentPointIndex.value =
            if(_currentPointIndex.value == 0)  {
                1
            }
            else {
                0
            }*/
    }

    fun updateDistanceToNextPoint(location: Location) {
        val currentLocation = Location("currentLocation")
        currentLocation.latitude = requireNotNull(points.value?.get(requireNotNull(currentPointIndex.value))?.pointLatitude)
        currentLocation.longitude = requireNotNull(points.value?.get(requireNotNull(currentPointIndex.value))?.pointLongitude)

        _distanceToNextPoint.value = currentLocation.distanceTo(location)

        // todo: if distancetonextpoint < 100m then navigate to quiz
    }
    /** Cancels all coroutines when the ViewModel is cleared, to cleanup any pending work. */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        Log.d("GameViewModel", "HomeViewModel destroyed!") // todo: delete if not needed
    }
}