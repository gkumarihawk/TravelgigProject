$(document).ready(function() {
  // Function to toggle the navbar dropdown
  $('.navbar .dropdown-toggle').on('click', function(e) {
    $(this).next('.dropdown-menu').toggle();
    e.stopPropagation();
  });

  // Close dropdown when clicking outside of it
  $(document).on('click', function(e) {
  if (!$(e.target).closest('.navbar .dropdown').length) {
  $('.dropdown-menu').hide();
  }
  });
  
  // Function to fetch recent bookings of the logged-in user
    function fetchRecentBookings(userName) {
        // Make an AJAX request to fetch the recent bookings
        $.get("http://localhost:8484/findBookingByUserName/" + userName, function(response, status) {
            // Clear the existing content of the table body
            $("#booking-table-body").empty();

            // Iterate over the bookings and populate the table rows
            $.each(response, function(index, booking) {
				alert(booking.bookingId);
                // Append a new row to the table body
                
                var bookingId = booking.bookingId;
                var hotelId = booking.hotelId;
                var bookingDate = booking.bookingDate;
                var checkInDate = booking.checkInDate;
                var checkOutDate = booking.checkOutDate;
                var customerMobile = booking.customerMobile;
                var price = booking.price;
                var status = booking.status;
                
                /*$("#booking-table-body").append(
                    "<tr>" +
                    "<td>" + booking.bookingId + "</td>" +
                    "<td class='d-none'>" + booking.hotelId + "</td>" +
                    "<td>" + booking.bookingDate + "</td>" +
                    "<td>" + booking.checkInDate + "</td>" +
                    "<td>" + booking.checkOutDate + "</td>" +
                    "<td>" + booking.customerMobile + "</td>" +
                    "<td>" + booking.price + "</td>" +
                    "<td>" + booking.status + "</td>" +
             
                    "</tr>"
                );*/
                $("#booking-table-body").append("<tr><td>"+bookingId+"</td><td>"+hotelId+"</td><td>"+bookingDate+"</td><td>"+checkInDate+"</td><td>"+checkOutDate+"</td></tr>")
            });
        });
    }
	
	// Event listener for user profile link click
	//$(".dropdown-item[href='/userProfile']").click(function() {
    	//event.preventDefault(); // Prevent default link behavior

    // Get the logged-in user's username
    	var loggedInUserName = $('#logged-in-user-name').val();

    // Call the fetchRecentBookings function with the logged-in user's username
    	fetchRecentBookings(loggedInUserName);
//});

  
  
  
  
  
});