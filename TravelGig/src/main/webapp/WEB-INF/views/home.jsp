<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home Page of Travel Gig</title>
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script src="./js/hotel.js"></script>
<!-- <script src="./js/userProfile.js"></script> -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %> 

</head>

<body>

<!-- Navbar -->
    <nav class="navbar navbar-dark bg-primary justify-content-between">
      <a class="navbar-brand" href="/">TravelGig</a>
      <ul class="navbar-nav">
        <li class="nav-item dropdown">
          <a
            class="nav-link dropdown-toggle"
            href="#"
            id="navbarDropdownMenuLink"
            data-toggle="dropdown"
            aria-haspopup="true"
            aria-expanded="false"
          >
            <security:authorize access="isAuthenticated()">
               Welcome,<span 
                 id="username" 
                 class="font-weight-bold font-italic ml-1" 
                ><security:authentication property="principal.username"
              /></span>
                    <!-- Hidden input fields for user information -->
			<input type="hidden" id="logged-in-user-name" value="<security:authentication property='principal.username' />">
			
            </security:authorize>
          </a>
          <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
            <a class="dropdown-item" href="/userProfile">My Bookings</a>
            <security:authorize access="!isAuthenticated()">
            <a class="dropdown-item" href="/login">Login</a>
            </security:authorize>
            <security:authorize access="isAuthenticated()">
            <a class="dropdown-item" href="/login?logout">Logout</a>
            </security:authorize>
            <security:authorize access="hasAuthority('ADMIN')">
            <a id="viewUnansweredQuestionsBtn" class="dropdown-item" href="#">View Unanswered Questions</a>
            </security:authorize>
            <security:authorize access="hasAuthority('USER')">
            <a id="viewAnsweredQuestionsBtn" class="dropdown-item" href="#">My Answered Questions</a>
            </security:authorize>
          </div>
        </li>
      </ul>
    </nav>
    
<div class="container" style="margin-left:100px">
<h1>Welcome to Travel Gig</h1>
<h2>Search your desired hotel</h2>
</div>

<div class="container border rounded" style="margin:auto;padding:50px;margin-top:50px;margin-bottom:50px">
	<h3>Narrow your search results</h3>
	<div class="form-row">
	<div class="col-3">
		Hotel/City/State/Address <input class="form-control" type="text" id="searchLocation" name="searchLocation"/>
	</div>
	<div class="col-2">
		No. Rooms: <input class="form-control" type="number" id="noRooms" name="noRooms"/>
	</div>
	<div class="col-2">
		No. Guests: <input class="form-control" type="number" id="noGuests" name="noGuests"/>
	</div>
	<div class="col">
	Check-In Date: <input type="date" id="checkInDate" name="checkInDate"/>
	</div>
	<div class="col">
	Check-Out Date: <input type="date" id="checkOutDate" name="checkOutDate"/>
	</div>
	<input class="btn-sm btn-primary" type="button" id="searchBtn" value="SEARCH"/>
	</div>
</div>

<div class="row">
<div class="col-2 border rounded" style="margin-left:50px;padding:25px">
	
	<br>	
	<!--  Star Rating: 
	<select class="form-control" id="filter_starRating">
		<option value=0>Select</option>
		<option value=1>1</option>
		<option value=2>2</option>
		<option value=3>3</option>
		<option value=4>4</option>
		<option value=5>5</option>
	</select><br>--> 
	
	Star Rating:<br>
	<div class="form-check-inline">
		<label class="form-check-label">
			<input type="checkbox" class="star_rating form-check-input" id="1_star_rating" value=1>1
		</label>
	</div>
	<div class="form-check-inline">
		<label class="form-check-label">
			<input type="checkbox" class="star_rating form-check-input" id="2_star_rating" value=2>2		
		</label>
	</div>
	<div class="form-check-inline">
		<label class="form-check-label">
			<input type="checkbox" class="star_rating form-check-input" id="3_star_rating" value=3>3
		</label>
	</div>
	<div class="form-check-inline">
		<label class="form-check-label">
			<input type="checkbox" class="star_rating form-check-input" id="4_star_rating" value=4>4
		</label>
	</div>
	<div class="form-check-inline">
		<label class="form-check-label">
			<input type="checkbox" class="star_rating form-check-input" id="5_star_rating" value=5>5
		</label>
	</div><br><br>
	
	Range:
	<div class="slidecontainer">
  		<input type="range" min="1" max="500" value="500" class="slider" id="priceRange">
  		<p>Price: $<span id="priceValue"></span></p>
	</div>
	
	<div class="form-check">
		<input type="checkbox" class="hotel_amenity form-check-input" id="amenity_parking" value="PARKING"/>
		<label class="form-check-label" for="amenity_parking">Parking</label><br>
		
		<input type="checkbox" class="hotel_amenity form-check-input" id="amenity_checkin_checkout" value="CHECK-IN & CHECK-OUT TIMES"/>
		<label class="form-check-label" for="amenity_checkin_checkout">Check-In & Check-Out Times</label><br>
		
		<input type="checkbox" class="hotel_amenity form-check-input" id="amenity_breakfast" value="BREAKFAST"/>
		<label class="form-check-label" for="amenity_breakfast">Breakfast</label><br>
		
		<input type="checkbox" class="hotel_amenity form-check-input" id="amenity_bar_lounge" value="BAR OR LOUNGE"/>
		<label class="form-check-label" for="amenity_bar_lounge">Bar / Lounge</label><br>
		
		<input type="checkbox" class="hotel_amenity form-check-input" id="amenity_fitness_center" value="FITNESS CENTER"/>
		<label class="form-check-label" for="amenity_fitness_center">Fitness Center</label><br>
	</div>
	
	<input style="margin-top:25px" class="btn btn-primary" type="button" id="filterBtn" value="FILTER"/>	
</div>


<div class="col-7 border rounded" style="margin-left:50px;">
	<div style='text-align:center;font-size:20px;font-family:"Trebuchet MS", Helvetica, sans-serif'>List of Hotels:</div>	
	
	<div id="listHotel">
	<table id="hotelTbl" border='1'>
		<tr class='header'><th>Name</th><th>Image</th><th>Star</th><th>Reviews</th><th>Questionnaires</th><td>
		
	</table>
	
	</div>
	
</div>
</div>

<div class="modal" id="myModal">  <!-- This modal needs to be show with a link. We need to implement this. a hyper link and the model will open with the details of the modals and the room type as well -->
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">Search Hotel Rooms</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

      <!-- Modal body -->
      <div class="modal-body">        
        <div class="col">
        	<input class="form-control" type="hidden" id="modal_hotelId"/>
        	Hotel Name: <input readonly="true" class="form-control" type="text" id="modal_hotelName"/>
        	No. Guests: <input class="form-control" type="number" id="modal_noGuests"/>
        	Check-In Date: <input class="form-control" type="date" id="modal_checkInDate"/>
        	Check-Out Date: <input class="form-control" type="date" id="modal_checkOutDate"/>
        	Room Type: <!-- For this we will have to make an ajax call. We have the RoomType entity which is connected to HotelRoom and HotelRoom is connected to Hotel. Just the name -->
        	<select class="form-control" id="select_roomTypes">
        	</select>
        	No. Rooms: <input class="form-control" type="number" id="modal_noRooms"/>
        	<input style="margin-top:25px" class="btn btn-searchHotelRooms form-control btn-primary" type="button" id="" value="SEARCH"/>       	
        </div>
        
     
        
        
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
      </div>

    </div>
  </div>
</div>

<div class="modal" id="hotelRoomsModal">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">Are these details correct?</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

      <!-- Modal body -->
      <div class="modal-body" id="hotelRooms_modalBody">        
              
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
      </div>

    </div>
  </div>
</div>

<div class="modal" id="bookingHotelRoomModal">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title"></h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

      <!-- Modal body -->
      <div class="modal-body" id="bookingRoom_modalBody">        
        	<div class="col">
       			<div><input class="form-control" type="hidden" id="booking_hotelId"/></div>
       			<div><input class="form-control" type="hidden" id="booking_hotelRoomId"/></div>
	        	<div>Hotel Name: <input readonly="true" class="form-control" type="text" id="booking_hotelName"/></div>
	        	<div>Customer Mobile: <input class="form-control" type="text" id="booking_customerMobile"/></div>
       			<div id="noGuestsDiv">No. Guests: <input readonly="true" class="form-control" type="number" id="booking_noGuests"/></div>
       			<div>No. Rooms: <input readonly="true" class="form-control" type="number" id="booking_noRooms"/></div>
       			<div>Check-In Date: <input readonly="true" class="form-control" type="text" id="booking_checkInDate"/></div>
       			<div>Check-Out Date: <input readonly="true" class="form-control" type="text" id="booking_checkOutDate"/></div>
       			<div>Room Type: <input readonly="true" class="form-control" type="text" id="booking_roomType"/></div>
       			<div>Discount: $<span id="booking_discount"></span></div>
       			<div>Total Price: $<span id="booking_price"></span></div>       			
       			<div style='margin-top:20px'>
       				
       				<security:authorize access="isAuthenticated()">
        				<button class='btn-confirm-booking btn btn-primary'>Confirm Booking</button>
        			</security:authorize>
        			
        			<security:authorize access="!isAuthenticated()">
        				<button class='btn-notConfirm-booking btn btn-primary'>Please login to Confirm</button>
        			</security:authorize>
   				
       				<button class='btn-edit btn btn-primary'>Edit</button>
       			</div>
        	</div>          
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
      </div>

    </div>
  </div>
</div>

<div class="modal" id="guestInfoModal">
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">Guest Information</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

      <!-- Modal body -->
      <div class="modal-body">
        <div class="form-group">
          <label for="firstName">First Name:</label>
          <input type="text" class="form-control" id="firstName" placeholder="Enter first name">
        </div>
        <div class="form-group">
          <label for="lastName">Last Name:</label>
          <input type="text" class="form-control" id="lastName" placeholder="Enter last name">
        </div>
        <div class="form-group">
         <label for="phoneNumber">Phone Number:</label>
         <input type="text" class="form-control" id="phone" readonly>
    	</div>
        <div class="form-group">
          <label for="age">Age:</label>
          <input type="number" class="form-control" id="age" placeholder="Enter age">
        </div>
        <div class="form-group">
          <label for="gender">Gender:</label>
          <select class="form-control" id="gender">
            <option value="male">Male</option>
            <option value="female">Female</option>
            <option value="other">Other</option>
          </select>
        </div>
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="saveGuestInfo">Save</button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>

    </div>
  </div>
</div>


<!--  For Reviews -->
<div class="modal fade" id="reviewsModal" tabindex="-1" role="dialog" aria-labelledby="reviewsModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="reviewsModalLabel">Reviews</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <div id="reviews-list"></div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>


 <!-- Question Modal -->
<div class="modal fade" id="questionModal" tabindex="-1" aria-labelledby="questionModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="questionModalLabel">Ask a Question</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="questionForm">
                    <input type="hidden" id="hotelId" name="hotelId"> 
                    <div class="mb-3">
                        <label for="question" class="form-label">Your Question</label>
                        <textarea class="form-control" id="question" name="question" rows="3" required></textarea>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Submit Question</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<!-- Recent questions Modal -->
<div class="modal fade" id="recentQuestionsModal" tabindex="-1" role="dialog" aria-labelledby="recentQuestionsModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="recentQuestionsModalLabel">Recent Questions</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <!-- This div will be populated with questions -->
                <div id="allQuestionsList"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<!-- Q/A for USER -->
<div class="modal" id="answeredQuestionsModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">My Answered Questions</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body" id="answeredQuestionsModalBody">
                <!-- Questions and answers will be populated here -->
                <div id="answeredQuestionsList">
                    <!-- This is where the questions and answers will be dynamically added -->
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>


<script>
var slider = document.getElementById("priceRange");
var output = document.getElementById("priceValue");
output.innerHTML = slider.value;
slider.oninput = function() {
	output.innerHTML = this.value;
}
</script>
</body>
</html>