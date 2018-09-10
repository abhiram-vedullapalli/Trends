<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1, sh">

<title>Vote Here</title>
</head>
<body>
<div align="center">
<form action="/vote" method="post">
<label>Enter your name</label><br><br>
<input type="text" name="username"><br><br>
<label>Select your State</label><br>
<select name="State">
	<option value="AP">Andhra Pradesh</option>
	<option value="UP">Uttar Pradesh</option>
	<option value="TN">TamilNadu</option>
</select><br><br>
<label>Select your Constituency</label><br>
<select name="Loksabha">
		<option value="Eluru">Eluru</option>
		<option value="Vijayawada">Vijayawada</option>
		<option value="Vizag">Vizag</option>
		<option value="Lucknow">Lucknow</option>
		<option value="Kanpur">Kanpur</option>
		<option value="Gorakhpur">Gorakhpur</option>
		<option value="Madurai">Madurai</option>
		<option value="Coimbatore">Coimbatore</option>
		<option value="Chennai">Chennai</option>
</select><br><br>

<label>Whom do you prefer MP ? </label><br>
<select name="MP">
	<option value="TDP">TDP</option>
	<option value="JSP">JSP</option>
	<option value="YSRCP">YSRCP</option>
		<option value="INC">INC</option>
		<option value="AIADMK">AIADMK</option>
		<option value="Rajnikanth's Party">Rajnikanth</option>
		<option value="BSP">BSP</option>
		<option value="SP">SP</option>
	
	<option value="DMK">DMK</option>
	<option value="BJP">BJP</option>
</select><br><br>

<label>Select your assembly constituency</label><br>
<select name="Assembly">
		<option value="Eluru">Eluru</option>
		<option value="Polavaram">Polavaram</option>
		<option value="Chinthalapudi">Chinthalapudi</option>
		<option value="Agra">Agra</option>
		<option value="Gurugram">Gurugram</option>
		<option value="Prayag">Prayag</option>
		<option value="Salem">Salem</option>
		<option value="Vellore">Vellore</option>
		<option value="Erode">Erode</option>
</select><br><br>

<label>Whom do you prefer for MLA ? </label><br>
<select name="MLA">
	<option value="TDP">TDP</option>
	<option value="JSP">JSP</option>
	<option value="YSRCP">YSRCP</option>
		<option value="INC">INC</option>
		<option value="AIADMK">AIADMK</option>
		<option value="Rajnikanth's Party">Rajnikanth</option>
		<option value="BSP">BSP</option>
		<option value="SP">SP</option>
	
	<option value="DMK">DMK</option>
	<option value="BJP">BJP</option>
	</select><br><br>

<input type="submit" value="Vote" name="Vote">
</form>
</div>
</body>
</html>