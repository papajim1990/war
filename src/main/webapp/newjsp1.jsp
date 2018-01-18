<%@page import="Beans.UserBean"%>
<%@page import="Beans.EventBean"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="org.jsoup.select.Elements"%>
<%@page import="org.jsoup.nodes.Element"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@page import="org.jsoup.Jsoup"%>
<link href="Css/fontawesome.min.css" rel="stylesheet" type="text/css"/>
<link href="Css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="Css/fontawesome-all.min.css" rel="stylesheet" type="text/css"/>
<link href="Css/fontawesome.min.css" rel="stylesheet" type="text/css"/>
<link href="Css/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="Css/Style.css">


<%
    HttpSession sessionn = request.getSession();
    Document doc = null;
    if (request.getAttribute("parameterUrl") != null) {
        doc = (Document) request.getAttribute("parameterUrl");
    } else {
        doc = Jsoup.connect("https://booking.com").get();
    }
    if(doc.getElementsByClass("sb-searchbox__outer")!=null){
    Elements elementsByClass = doc.getElementsByClass("sb-searchbox__outer");
    for (Element form : elementsByClass) {
        for (Element input : form.getElementsByTag("form")) {
            input.attr("action", "UrlContoller");
            input.append("<input type='hidden' name='form-sarch' value='ok'></input>");
        }

    }
    }
    if(doc.getElementById("b_google_map_thumbnail")!=null){
    String map = doc.getElementById("b_google_map_thumbnail").attr("data-map");
    }
    if (sessionn.getAttribute("user") != null) {
        UserBean user = (UserBean) sessionn.getAttribute("user");

        doc.getElementById("add_property_topbar").remove();
        doc.getElementById("current_account").remove();

        Element Register = doc.getElementById("current_account");
        doc.getElementsByAttributeValue("data-target", "#myModalll").remove();
        Register.select("a").remove();
        Register.attr("data-command", "");
        Register.append("<a class='popover_trigger login-message'>Welcome " + user.getFirstname() + "</a>");
        doc.getElementById("user_form").select("ul").get(0).append("<li class='account_register_option user_center_option uc_account'><button onclick='logout()' type='button' id='logout-button' class='btn btn-primary'>Log out</button></li>");
    } else {
        Element Register = doc.getElementById("current_account");
        Register.select("a").remove();
        Register.attr("data-command", "");
        Register.append("<button type='button' class='btn btn-primary signup' data-toggle='modal' data-target='#myModall'>Sign Up</button>");
        Element Register1 = doc.getElementsByClass("account_register_option").get(0);
        Register1.select("a").remove();
        Register1.attr("data-command", "");
        Register1.append("<button type='button'  class='btn btn-primary login' data-toggle='modal' data-target='#myModalll'>Log In</button>");
    }
    if (doc.getElementById("hotellist_inner") != null) {
        Element layout = doc.getElementById("hotellist_inner");
        layout.prepend("<div id='accordion' role='tablist' aria-multiselectable='true'></div>");
    }
    if (request.getAttribute("EventsList") != null) {
        List<EventBean> Events = (List<EventBean>) request.getAttribute("EventsList");
        for (EventBean Event : Events) {
            Element layoutin = doc.getElementById("accordion");
            layoutin.append("");

        }
    }
    out.print(doc.toString());
%>
<script src="Scripts/jquery-3.2.1.min.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>

<script src="Scripts/jquery-ui.min_1.js" type="text/javascript"></script>
<script src="Scripts/bootstrap.min.js" type="text/javascript"></script>

<script src="Scripts/Scripts.js" type="text/javascript"></script>












<script>


    $("#frm").submit(function (e) {

        var url = "UrlContoller"; // the script where you handle the form input.

        $.ajax({
            type: "GET",
            url: url,
            data: $("#frm").serialize(), // serializes the form's elements.
            success: function (data)
            {

            }
        });
        // avoid to execute the actual submit of the form.
    });</script>

<script>

    $(document).ready(function () {
    <% if (request.getAttribute("ErrorValidation") != null) {
             out.print("$('.signup').click(); $('#problemsignup').css('display','block');");
         }%>
    <%
        if (session.getAttribute("ErrorValidation-Fb") != null) {
            out.print("$('.login').click(); $('#problemlogin').css('display','block');");
        }
    %>

    });


</script>
<div class="modal fade" id="myModalll" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Create an account</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="Home?action=signin" method="post">
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12">


                                <div class="form-group margin-form">
                                    <div class="alert alert-warning alert-dismissible fade show" id="problemlogin" role="alert">
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                        <%if (session.getAttribute("ErrorValidation-Fb") != null) {%>
                                        <strong>There was a problem !</strong> <%=session.getAttribute("ErrorValidation-Fb").toString()%>
                                        <%}%>
                                    </div>
                                    <label for="formGroupExampleInput">Email</label>
                                    <input type="text" class="form-control" id="formGroupExampleInput" name="emailLogin" placeholder="example@host.com">
                                </div>
                                <div class="form-group margin-form">
                                    <label for="formGroupExampleInput2">Password</label>
                                    <input type="password" class="form-control" id="formGroupExampleInput2" name="passwordLogin" placeholder="Type your password">
                                </div>

                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 ">
                                <h4 class="text-center">Connect using your account at</h4>
                                <div class="row">
                                    <div class="col-md-6"><div class="brand-box-login"><h4 class="text-center"><i class="fab fa-facebook-square"></i>acebook</h4></div></div>
                                    <div class="col-md-6"><div class="brand-box-gmail-signin"><h4 class="text-center"><i class="fab fa-google"></i>mail</h4></div></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModall" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Create an account</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="Home?action=SignUp" method="post">
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-sm-12">
                                <ul id="nav-steps" class="nav nav-tabs" role="tablist">
                                    <li class="nav-item">
                                        <a class="nav-link active"   href="#step1">Step 1</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link"   href="#step2">Step 2</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link"  href="#step3">Complete</a>
                                    </li>

                                </ul>
                            </div>
                            <div class="col-sm-12">
                                <div class="progress">
                                    <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style=""></div>
                                </div>
                            </div>



                            <div class="tab-content col-md-12">
                                <div class="tab-pane active" role="tabpanel" id="step1">


                                    <div class="form-group margin-form">
                                        <div class="alert alert-warning alert-dismissible fade show" id="problemsignup" role="alert">
                                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                            <strong>There was a problem !</strong> Provide a valid email address and a password 6-12 letters/digits .
                                        </div>
                                        <label for="formGroupExampleInput">Email</label>
                                        <input required type="text" class="form-control" name="EmailUser" id="email" placeholder="example@host.com">
                                    </div>
                                    <div class="form-group margin-form">

                                        <label for="formGroupExampleInput2">Password</label>
                                        <input required type="password" class="form-control" name="PasswordUser" id="pass" placeholder="Type your password">
                                    </div>


                                </div>
                                <div class="tab-pane" role="tabpanel" id="step2">


                                    <div class="form-group margin-form">
                                        <label for="formGroupExampleInput">First Name</label>
                                        <input required type="text" class="form-control" name="FirstName" id="FirstName" placeholder="Your First Name">
                                    </div>
                                    <div class="form-group margin-form">
                                        <label for="formGroupExampleInput2">Last Name</label>
                                        <input required type="text" class="form-control" name="LastName" id="LastName" placeholder="Your Last Name">
                                    </div>
                                    <div class="form-group margin-form">
                                        <label class="mr-sm-2" for="inlineFormCustomSelect">Preference</label>
                                        <select name="Gender" class="custom-select mb-2 mr-sm-2 mb-sm-0" id="inlineFormCustomSelect">
                                            <option selected>Gender</option>
                                            <option value="Male">Male</option>
                                            <option value="Female">Female</option>
                                        </select>
                                    </div>

                                </div>
                                <div class="tab-pane" role="tabpanel" id="step3">


                                    <div class="form-group margin-form" >
                                        <label for="formGroupExampleInput2">Select your Nationality</label>
                                        <select required class="custom-select mb-2 mr-sm-2 mb-sm-0" id="nationality" name="nationality">
                                            <option value="">-- select one --</option>
                                            <option value="afghan">Afghan</option>
                                            <option value="albanian">Albanian</option>
                                            <option value="algerian">Algerian</option>
                                            <option value="american">American</option>
                                            <option value="andorran">Andorran</option>
                                            <option value="angolan">Angolan</option>
                                            <option value="antiguans">Antiguans</option>
                                            <option value="argentinean">Argentinean</option>
                                            <option value="armenian">Armenian</option>
                                            <option value="australian">Australian</option>
                                            <option value="austrian">Austrian</option>
                                            <option value="azerbaijani">Azerbaijani</option>
                                            <option value="bahamian">Bahamian</option>
                                            <option value="bahraini">Bahraini</option>
                                            <option value="bangladeshi">Bangladeshi</option>
                                            <option value="barbadian">Barbadian</option>
                                            <option value="barbudans">Barbudans</option>
                                            <option value="batswana">Batswana</option>
                                            <option value="belarusian">Belarusian</option>
                                            <option value="belgian">Belgian</option>
                                            <option value="belizean">Belizean</option>
                                            <option value="beninese">Beninese</option>
                                            <option value="bhutanese">Bhutanese</option>
                                            <option value="bolivian">Bolivian</option>
                                            <option value="bosnian">Bosnian</option>
                                            <option value="brazilian">Brazilian</option>
                                            <option value="british">British</option>
                                            <option value="bruneian">Bruneian</option>
                                            <option value="bulgarian">Bulgarian</option>
                                            <option value="burkinabe">Burkinabe</option>
                                            <option value="burmese">Burmese</option>
                                            <option value="burundian">Burundian</option>
                                            <option value="cambodian">Cambodian</option>
                                            <option value="cameroonian">Cameroonian</option>
                                            <option value="canadian">Canadian</option>
                                            <option value="cape verdean">Cape Verdean</option>
                                            <option value="central african">Central African</option>
                                            <option value="chadian">Chadian</option>
                                            <option value="chilean">Chilean</option>
                                            <option value="chinese">Chinese</option>
                                            <option value="colombian">Colombian</option>
                                            <option value="comoran">Comoran</option>
                                            <option value="congolese">Congolese</option>
                                            <option value="costa rican">Costa Rican</option>
                                            <option value="croatian">Croatian</option>
                                            <option value="cuban">Cuban</option>
                                            <option value="cypriot">Cypriot</option>
                                            <option value="czech">Czech</option>
                                            <option value="danish">Danish</option>
                                            <option value="djibouti">Djibouti</option>
                                            <option value="dominican">Dominican</option>
                                            <option value="dutch">Dutch</option>
                                            <option value="east timorese">East Timorese</option>
                                            <option value="ecuadorean">Ecuadorean</option>
                                            <option value="egyptian">Egyptian</option>
                                            <option value="emirian">Emirian</option>
                                            <option value="equatorial guinean">Equatorial Guinean</option>
                                            <option value="eritrean">Eritrean</option>
                                            <option value="estonian">Estonian</option>
                                            <option value="ethiopian">Ethiopian</option>
                                            <option value="fijian">Fijian</option>
                                            <option value="filipino">Filipino</option>
                                            <option value="finnish">Finnish</option>
                                            <option value="french">French</option>
                                            <option value="gabonese">Gabonese</option>
                                            <option value="gambian">Gambian</option>
                                            <option value="georgian">Georgian</option>
                                            <option value="german">German</option>
                                            <option value="ghanaian">Ghanaian</option>
                                            <option value="greek">Greek</option>
                                            <option value="grenadian">Grenadian</option>
                                            <option value="guatemalan">Guatemalan</option>
                                            <option value="guinea-bissauan">Guinea-Bissauan</option>
                                            <option value="guinean">Guinean</option>
                                            <option value="guyanese">Guyanese</option>
                                            <option value="haitian">Haitian</option>
                                            <option value="herzegovinian">Herzegovinian</option>
                                            <option value="honduran">Honduran</option>
                                            <option value="hungarian">Hungarian</option>
                                            <option value="icelander">Icelander</option>
                                            <option value="indian">Indian</option>
                                            <option value="indonesian">Indonesian</option>
                                            <option value="iranian">Iranian</option>
                                            <option value="iraqi">Iraqi</option>
                                            <option value="irish">Irish</option>
                                            <option value="israeli">Israeli</option>
                                            <option value="italian">Italian</option>
                                            <option value="ivorian">Ivorian</option>
                                            <option value="jamaican">Jamaican</option>
                                            <option value="japanese">Japanese</option>
                                            <option value="jordanian">Jordanian</option>
                                            <option value="kazakhstani">Kazakhstani</option>
                                            <option value="kenyan">Kenyan</option>
                                            <option value="kittian and nevisian">Kittian and Nevisian</option>
                                            <option value="kuwaiti">Kuwaiti</option>
                                            <option value="kyrgyz">Kyrgyz</option>
                                            <option value="laotian">Laotian</option>
                                            <option value="latvian">Latvian</option>
                                            <option value="lebanese">Lebanese</option>
                                            <option value="liberian">Liberian</option>
                                            <option value="libyan">Libyan</option>
                                            <option value="liechtensteiner">Liechtensteiner</option>
                                            <option value="lithuanian">Lithuanian</option>
                                            <option value="luxembourger">Luxembourger</option>
                                            <option value="macedonian">Macedonian</option>
                                            <option value="malagasy">Malagasy</option>
                                            <option value="malawian">Malawian</option>
                                            <option value="malaysian">Malaysian</option>
                                            <option value="maldivan">Maldivan</option>
                                            <option value="malian">Malian</option>
                                            <option value="maltese">Maltese</option>
                                            <option value="marshallese">Marshallese</option>
                                            <option value="mauritanian">Mauritanian</option>
                                            <option value="mauritian">Mauritian</option>
                                            <option value="mexican">Mexican</option>
                                            <option value="micronesian">Micronesian</option>
                                            <option value="moldovan">Moldovan</option>
                                            <option value="monacan">Monacan</option>
                                            <option value="mongolian">Mongolian</option>
                                            <option value="moroccan">Moroccan</option>
                                            <option value="mosotho">Mosotho</option>
                                            <option value="motswana">Motswana</option>
                                            <option value="mozambican">Mozambican</option>
                                            <option value="namibian">Namibian</option>
                                            <option value="nauruan">Nauruan</option>
                                            <option value="nepalese">Nepalese</option>
                                            <option value="new zealander">New Zealander</option>
                                            <option value="ni-vanuatu">Ni-Vanuatu</option>
                                            <option value="nicaraguan">Nicaraguan</option>
                                            <option value="nigerien">Nigerien</option>
                                            <option value="north korean">North Korean</option>
                                            <option value="northern irish">Northern Irish</option>
                                            <option value="norwegian">Norwegian</option>
                                            <option value="omani">Omani</option>
                                            <option value="pakistani">Pakistani</option>
                                            <option value="palauan">Palauan</option>
                                            <option value="panamanian">Panamanian</option>
                                            <option value="papua new guinean">Papua New Guinean</option>
                                            <option value="paraguayan">Paraguayan</option>
                                            <option value="peruvian">Peruvian</option>
                                            <option value="polish">Polish</option>
                                            <option value="portuguese">Portuguese</option>
                                            <option value="qatari">Qatari</option>
                                            <option value="romanian">Romanian</option>
                                            <option value="russian">Russian</option>
                                            <option value="rwandan">Rwandan</option>
                                            <option value="saint lucian">Saint Lucian</option>
                                            <option value="salvadoran">Salvadoran</option>
                                            <option value="samoan">Samoan</option>
                                            <option value="san marinese">San Marinese</option>
                                            <option value="sao tomean">Sao Tomean</option>
                                            <option value="saudi">Saudi</option>
                                            <option value="scottish">Scottish</option>
                                            <option value="senegalese">Senegalese</option>
                                            <option value="serbian">Serbian</option>
                                            <option value="seychellois">Seychellois</option>
                                            <option value="sierra leonean">Sierra Leonean</option>
                                            <option value="singaporean">Singaporean</option>
                                            <option value="slovakian">Slovakian</option>
                                            <option value="slovenian">Slovenian</option>
                                            <option value="solomon islander">Solomon Islander</option>
                                            <option value="somali">Somali</option>
                                            <option value="south african">South African</option>
                                            <option value="south korean">South Korean</option>
                                            <option value="spanish">Spanish</option>
                                            <option value="sri lankan">Sri Lankan</option>
                                            <option value="sudanese">Sudanese</option>
                                            <option value="surinamer">Surinamer</option>
                                            <option value="swazi">Swazi</option>
                                            <option value="swedish">Swedish</option>
                                            <option value="swiss">Swiss</option>
                                            <option value="syrian">Syrian</option>
                                            <option value="taiwanese">Taiwanese</option>
                                            <option value="tajik">Tajik</option>
                                            <option value="tanzanian">Tanzanian</option>
                                            <option value="thai">Thai</option>
                                            <option value="togolese">Togolese</option>
                                            <option value="tongan">Tongan</option>
                                            <option value="trinidadian or tobagonian">Trinidadian or Tobagonian</option>
                                            <option value="tunisian">Tunisian</option>
                                            <option value="turkish">Turkish</option>
                                            <option value="tuvaluan">Tuvaluan</option>
                                            <option value="ugandan">Ugandan</option>
                                            <option value="ukrainian">Ukrainian</option>
                                            <option value="uruguayan">Uruguayan</option>
                                            <option value="uzbekistani">Uzbekistani</option>
                                            <option value="venezuelan">Venezuelan</option>
                                            <option value="vietnamese">Vietnamese</option>
                                            <option value="welsh">Welsh</option>
                                            <option value="yemenite">Yemenite</option>
                                            <option value="zambian">Zambian</option>
                                            <option value="zimbabwean">Zimbabwean</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <button class="btn btn-primary btn-lg btn-block" id="sendsignup" type="submit" value="go">sign up</button>
                                    </div>


                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" id="backStep" class="btn btn-secondary" >Close</button>
                            <button type="button" id="nextStep" class="btn btn-primary">next</button>
                        </div>
                        <div class="row">
                            <div class="col-md-12 ">
                                <h4 class="text-center">Connect using your account at</h4>
                                <div class="row">
                                    <div class="col-md-6"><div class="brand-box-sign-up"><h4 class="text-center"><i class="fab fa-facebook-square"></i>acebook</h4></div></div>
                                    <div class="col-md-6"><div class="brand-box-gmail-signup"><h4 class="text-center"><i class="fab fa-google"></i>mail</h4></div></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>
<div class="modal fade" id="mapEvents" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="false">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div id="mapforevents"></div>
        </div>
    </div>
</div>
<script>

    function stoperror() {
        return true;
    }
    window.onerror = stoperror;
    var mapp;
    function initMap() {
      if($("#sort_by").length!==0){
        var uluru = {lat: -25.363, lng: 131.044};
        var map = new google.maps.Map(document.getElementById('mapforevents'), {
            zoom: 7,
            center: uluru
        });
    var LocationCity = $('[name="ssne"]').val().split(",")[0];
        $.ajax({
            url: 'Home?action=mapMarkers',
            contentType: "json",
            data: {"city": LocationCity},
            type: 'GET',
            cache: false,
            success: function (result) {
                var Hotels = result.Hotels;

                
                for (var i = 0; i < Hotels.length; i++) {
                    var k=0;
                    var Location = JSON.parse(Hotels[i].Location);
                    var Info = JSON.parse(Hotels[i].Info);
                    var urll=String(Info.hotelUrl);
                            var url=urll.replace("http://www.booking.com/","/UrlContoller?q=").replace("highlight_room=#hotelTmpl","")+"&parameterUrl=link";
                   // alert(url);
                    var contentString = '<div id="content'+i+'">'+'<div id="siteNotice'+i+'">'+'</div>'+'<h1 id="firstHeading'+i+'" class="firstHeading">'+String(Info.hoteName)+'</h1>'+'<div id="bodyContent">'+            '<a  href="'+encodeURI(url)+'">'+'see the hotel page'+'</a> '+
            '</div>'+
            '</div>';

        var infowindow = new google.maps.InfoWindow({
          content: contentString
        });
                    var icon = {
    url: "Css\\15-512.png", // url
    scaledSize: new google.maps.Size(30, 30), // scaled size
    origin: new google.maps.Point(0,0), // origin
    anchor: new google.maps.Point(0, 0) // anchor
};
                    //;
                    //
                     var marker = new google.maps.Marker({
                position: new google.maps.LatLng(Location.hoteLat, Location.hoteLong),
                icon:icon,
                map: mapp
            });
          google.maps.event.addListener(marker,'click', (function(marker,contentString,infowindow){ 
    return function() {
        infowindow.setContent(contentString);
        infowindow.open(mapp,marker);
    };
})(marker,contentString,infowindow));
                   
                }
            }
        });

         
        
        mapp = map;
    }
    }
     
      $(document).ready(function(){
          
      });
 
</script>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDHClomzxAUxQgq4EHGQ7S_CdBLvzXHHO0&callback=initMap">
</script>


<div class="modalpagebody"></div>