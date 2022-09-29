package project;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;


//Integrantes:
//Javier Moraga / Eduardo Miranda


public class Taller_1 
{

	public static void main(String[] args) throws IOException
	{
		File file = new File("h.txt");
		Scanner scan = new Scanner(file);

		System.out.println("Base de datos humana");
		
		String [] nationalities = new String[0];
		String [] names = new String[0];
		String [] IDs = new String[0];
		String [] regions = new String[0];
		String [] cities = new String[0];
		int [] ages = new int[0];
		float [] heights = new float[0];
		float [] weights = new float[0];
		String [] wPlanets = new String[0];
		
		int cont = 0;
		while(scan.hasNextLine()) //human
		{	
			String line = scan.nextLine();
			String[] parts = line.split(",");
			
			String nationality = parts[0]; nationalities= AppendStr(nationalities,nationality);
			String name = parts[1]; names= AppendStr(names,name);
			String id = parts[2]; IDs= AppendStr(IDs,id);
			String region = parts[3]; regions= AppendStr(regions,region);
			String city = parts[4]; cities= AppendStr(cities,city);
			int age = Integer.parseInt(parts[5]); ages= AppendInt(ages,age);
			float height = Float.parseFloat(parts[6])/100; heights= AppendFl(heights,height);
			float weight = Float.parseFloat(parts[7])/1000; weights= AppendFl(weights,weight);
			String wPlanet = parts[8]; wPlanets= AppendStr(wPlanets,wPlanet);
			cont++;
		}scan.close();
		System.out.println(names[0]);
		if (cont==0) {
			System.out.println("El archivo de humanos no tiene datos.");
		}
		File file2 = new File("x.txt");
		Scanner scan2 = new Scanner(file2);
		
		System.out.println("Base de datos extraterrestre");
		
		String [] nameEspecie = new String[100];
		String [] name = new String[100];
		String [] alienID = new String[100];
		String [] planeta = new String[100];
		Float [] edades = new Float[100];
		Float [] ALTURA = new Float[100];
		Float [] PESO = new Float[100];
		String [] type = new String[100];
		int cont2 = 0; 
		while(scan2.hasNextLine()) //alien (requiere traducción)
		{
			String linea = scan2.nextLine();
			System.out.println(linea);
			String [] partes = linea.split(", ");
			
			String nombreEspecie_TL = Traduccion(partes[0]); nameEspecie[cont2] = nombreEspecie_TL;
			String nombre_TL = Traduccion(partes[1]).toUpperCase(); name[cont2] = nombre_TL;
			String identificacionUniversal = partes[2]; alienID[cont2] = identificacionUniversal;
			String planetaDeOrigen_TL = Traduccion(partes[3]).toUpperCase(); planeta[cont2] = planetaDeOrigen_TL;
			
			float edadEXT = Float.valueOf(partes[4]); 
			float edadEXT_TL = convertirEdad(edadEXT,"X"); edades[cont2] = edadEXT_TL;
			float altura = Float.valueOf(partes[5]); 
			float altura_TL = convertirAltura(altura); ALTURA[cont2] = altura_TL;
			
			float peso = Float.valueOf(partes[6]);
			float peso_TL = convertirPeso(peso); PESO[cont2] = peso_TL;
			
			String tipo = partes[7]; type[cont2] = tipo;
			

			cont2++; // assume every line of text is a new alien
		}

		ingresarETX(nameEspecie, name, alienID, planeta, edades, ALTURA, PESO, type); //1
		modifyALIEN(nameEspecie, name, alienID, planeta, edades, ALTURA, PESO, type); //2
		enterHuman(nationalities,names,IDs,regions,cities,ages,heights,weights,wPlanets); //3
		
		modifyHuman(nationalities,names,IDs,regions,cities,ages,heights,weights,wPlanets); // 4
		showByNationality(nationalities,names,IDs,regions,cities,ages,heights,weights,wPlanets); //5
		alienElimination(nameEspecie, name, alienID, planeta, edades, ALTURA, PESO, type); //6
		
		removeHuman(nationalities,names,IDs,regions,cities,ages,heights,weights,wPlanets); // 7
		showbyAlienID(alienID); 															//8
		showByPlanet(nameEspecie, name, alienID, planeta, edades, ALTURA, PESO, type, names, wPlanets); //9
		
		showStatsNationality(nationalities,names,IDs,regions,cities,ages,heights,weights,wPlanets,cont); // 10
		showByType(type); //11 (type is alien type list)
		scan2.close(); //end
	}

	//↓ methods
	private static void showStatsNationality(String[] nationalities, String[] names, String[] IDs, String[] regions,
			String[] cities, int[] ages, float[] heights, float[] weights, String[] wPlanets, int contGen) {
		
		Scanner scan = new Scanner(System.in);
		System.out.println("10) Ingrese nacionalidad a mostrar: ");
		String nationality = scan.nextLine();
		int cont =0;
		for (int i=0; i<nationalities.length; i++) {
			if (nationalities[i].equalsIgnoreCase(nationality)) {
				System.out.println(names[i]+", "+IDs[i]+", "+regions[i]+", "+cities[i]+", "+ages[i]+", "+
							heights[i]+", "+weights[i]+", "+wPlanets[i]);
				cont++;
			}
		}
		System.out.println("El porcentaje con respecto al total de humanos es: "+((cont*100)/contGen));
		
	}
	private static void removeHuman(String[] nationalities, String[] names, String[] IDs, String[] regions, String[] cities,
			int[] ages, float[] heights, float[] weights, String[] wPlanets) {
		
		Scanner scan = new Scanner(System.in);
		System.out.println("7) Eliminar humano (SI/NO): ");
		String option = scan.nextLine().toUpperCase();
		
		if(option.equals("NO")) {System.out.println("cerrando (7)");}
		
		while(option.equals("SI")) {
			String id = scan.nextLine();
			int index = getIndex(IDs,id);
			if(index==-1) {
				System.out.println("Identificacion no encontrada.");
			}else {
				removeStr(nationalities,index); removeStr(names,index);
				removeStr(IDs,index); removeStr(regions,index);
				removeStr(cities,index); removeInt(ages,index);
				removeFl(heights,index); removeFl(weights,index);
				removeStr(wPlanets,index);
				System.out.println("Humano eliminado de la base de datos.");
			}
			System.out.println("7) Eliminar humano (SI/NO): ");
			option = scan.nextLine().toUpperCase();
		}
	}
	
	private static void removeStr(String[] list, int index) {
		int i = index;
		for (int j=i+1; j<list.length; j++, i++) {
				list[i] = list[j];
		}
		String[]nList = new String[list.length-1];
		for (int k=0; k<list.length-1; k++) {
			nList[k] = list[k];
		}
		list=nList;
	}
	private static void removeInt(int[] list, int index) {
		int i = index;
		for (int j=i+1; j<list.length; j++, i++) {
				list[i] = list[j];
		}
		int[]nList = new int[list.length-1];
		for (int k=0; k<list.length-1; k++) {
			nList[k] = list[k];
		}
		list=nList;
	}
	private static void removeFl(float[] list, int index) {
		int i = index;
		for (int j=i+1; j<list.length; j++, i++) {
				list[i] = list[j];
		}
		float[]nList = new float[list.length-1];
		for (int k=0; k<list.length-1; k++) {
			nList[k] = list[k];
		}
		list=nList;
	}

	private static void showByNationality(String[] nationalities, String[] names, String[] IDs, String[] regions,
			String[] cities, int[] ages, float[] heights, float[] weights, String[] wPlanets) {
		
		Scanner scan = new Scanner(System.in);
		System.out.println("5) Mostrar por nacionalidad (SI/NO): ");
		String option = scan.nextLine().toUpperCase();
		
		if(option.equals("NO")) {System.out.println("cerrando (5)");}
		
		while(option.equals("SI")) {

			System.out.println("Ingrese nacionalidad a mostrar: ");
			String nationality = scan.nextLine();
			
			System.out.println("Humanos de nacionalidad ["+nationality+"]: ");
			for (int i=0; i<nationalities.length; i++) {
				if (nationalities[i].equalsIgnoreCase(nationality)) {
					System.out.print(names[i]+", "+IDs[i]+", "+regions[i]+", "+cities[i]+", "+ages[i]+", "+
							heights[i]+", "+weights[i]+", "+wPlanets[i]);
				}
			}
			System.out.println("5) Mostrar por nacionalidad (SI/NO): ");
			option = scan.nextLine().toUpperCase();
		}
		
	}
	private static void modifyHuman(String[] nationalities, String[] names, String[] IDs, String[] regions,
			String[] cities, int[] ages, float[] heights, float[] weights, String[] wPlanets) {
		
		Scanner scan = new Scanner(System.in);
		System.out.println("4) Modificar un humano (SI/NO): ");
		String option = scan.nextLine().toUpperCase();
		
		if(option.equals("NO")) {
			System.out.println("cerrando (4)");
		}
		
		while(option.equals("SI")) {
			System.out.println("Ingrese identificacion del humano a modificar: ");
			String modId = scan.nextLine();
			int index = getIndex(IDs,modId);
			if(index==-1) {
				System.out.println("Humano no encontrado.");
			}else {
				System.out.println("Ingrese nacionalidad: ");
				String newNation = scan.nextLine();
				nationalities[index] = newNation;
				System.out.println("Ingrese nombre: ");
				String newName = scan.nextLine();
				names[index] = newName;
				System.out.println("Ingrese identificación: ");
				String newId = scan.nextLine();
				IDs[index] = newId;
				System.out.println("Ingrese región: ");
				String newRegion = scan.nextLine();
				regions[index] = newRegion;
				System.out.println("Ingrese ciudad: ");
				String newCity = scan.nextLine();
				cities[index] = newCity;
				System.out.println("Ingrese edad: ");
				int newAge = Integer.parseInt(scan.nextLine());
				ages[index] = newAge;
				System.out.println("Ingrese altura (en metros): ");
				float newHeight = Float.parseFloat(scan.nextLine());
				heights[index] = newHeight;
				System.out.println("Ingrese peso (en kilogramos): ");
				float newWeight = Float.parseFloat(scan.nextLine());
				weights[index] = newWeight;
				System.out.println("Ingrese planeta de trabajo: ");
				String newWorkPlanet = scan.nextLine();
				wPlanets[index] = newWorkPlanet;
			}
			System.out.println("4) Modificar un humano (SI/NO): ");
			option = scan.nextLine().toUpperCase();
		}
		
	}

	private static int getIndex(String[] list, String fact) {
		for (int i=0; i<list.length; i++) {
			if(list[i].equals(fact)) {
				return i;
			}
		}

		return -1;
	}
	private static void enterHuman(String[] nationalities, String[] names, String[] IDs, String[] regions,
			String[] cities, int[] ages, float[] heights, float[] weights, String[] wPlanets) {
		
		Scanner scan = new Scanner(System.in);
		System.out.println("3) Ingresar un nuevo humano (SI/NO): ");
		String option = scan.nextLine().toUpperCase();
		if(option.equals("NO")) {
			System.out.println("No se ingresarán más humanos");
			
		}
		while(option.equals("SI")) {
			System.out.println("Ingrese nacionalidad: ");
			String nation = scan.nextLine();
			nationalities = AppendStr(nationalities,nation);
			System.out.println("Ingrese nombre: ");
			String name = scan.nextLine();
			names = AppendStr(names,name);
			System.out.println("Ingrese identificacion: ");
			String id = scan.nextLine();
			IDs = AppendStr(IDs,id);
			System.out.println("Ingrese región: ");
			String region = scan.nextLine();
			regions = AppendStr(regions,region);
			System.out.println("Ingrese ciudad: ");
			String city = scan.nextLine();
			cities = AppendStr(cities,city);
			System.out.println("Ingrese edad: ");
			int age = Integer.parseInt(scan.nextLine());
			ages = AppendInt(ages,age);
			System.out.println("Ingrese altura (en metros): ");
			float height = Float.parseFloat(scan.nextLine());
			heights = AppendFl(heights,height);
			System.out.println("Ingrese peso (en kilogramos): ");
			float weight = Float.parseFloat(scan.nextLine());
			weights = AppendFl(weights,weight);
			System.out.println("Ingrese planeta de trabajo: ");
			String wPlanet = scan.nextLine();
			wPlanets = AppendStr(wPlanets,wPlanet);
			
			System.out.println("3) Ingresar un nuevo humano (SI/NO): ");
			option = scan.nextLine().toUpperCase();
		}
		
	}
	private static String[] AppendStr(String[] lista, String valor) {
		String [] aux = new String[lista.length+1];
		for(int i = 0; i <lista.length;i++) {
			aux[i]=lista[i];
		}
		aux[lista.length] = valor;
		return aux;
	}
	
	private static float[] AppendFl(float[] lista, float valor) {
		float [] aux = new float[lista.length+1];
		for(int i = 0; i <lista.length;i++) {
			aux[i]=lista[i];
		}
		aux[lista.length] = valor;
		return aux;
	}

	private static int[] AppendInt(int[] lista, int valor) {
		int [] aux = new int[lista.length+1];
		for(int i = 0; i <lista.length;i++) {
			aux[i]=lista[i];
		}
		aux[lista.length] = valor;
		return aux;
	}

	public static String Traduccion(String linea) //recibe variable, traduce variable
	{
		String minus = linea.toLowerCase(); //formatear tExTo a texto
		String [] partes = minus.split("");
		String result = "";
		for (int i = 0 ;i < partes.length ; i++) 
		{if(partes[i].equals("a")) 
			{
				partes[i] = "e";
			}else if(partes[i].equals("e")) {
				partes[i] = "a";
			}else if(partes[i].equals("o")){
				partes[i] = "i";
			}else if(partes[i].equals("i")){
				partes[i] = "o";
			}
			//independiente del if agregamos letra a letra
			if (i == 0){  //la inicial mayuscula
				result += partes[i].toUpperCase();
			}else {
				result += partes[i];
			}
		}
		return result;
	}
	
	
	public static float convertirEdad(float edad,String verificar) // X = alien | H = human
	{	
		float edadResultante = edad;
		
		if(verificar == "X") 
		{
			edadResultante = edad/8;
		}
		else if(verificar == "H")
		{
			edadResultante = edad *8;
		}
		return edadResultante;
	}
	
	public static float convertirAltura(float altura)
	{
		float altura_R = altura /100; //height in cm
		return altura_R; //height in meters
	}
	
	public static float convertirPeso(float peso) // peso ext a humano
	{
		float peso_R = peso /1000;
		return peso_R; //peso en Kg
	}
	
	public static void ingresarETX(String[] nEsp, String[] name, String[] alienID, String[] planet, Float[] age, Float[] height, Float[] weight, String[] tipo)
	{	
		Scanner sc = new Scanner(System.in);
		System.out.println("1) Ingresar extraterrestre:(SI/NO)");
		
		String askk = sc.nextLine().toUpperCase();
		String ask = "";
		String verif = "true";
		

		if (askk.equals("NO")) 
		{	
			System.out.println("cerrando (1)");
			return;
		}
		
		System.out.println("nombre de la especie: ");
		askk = sc.nextLine();
		String temporal1 = ask;
		
		System.out.println("nombre del alien: ");
		ask = sc.nextLine();
		String temporal2 = ask;
		
		System.out.println("ID del alien: (8 digitos)");
		ask = sc.nextLine();
		String temporal3 = ask;
		
		System.out.println(verificarDigitos(8,temporal3));
		if(verificarDigitos(8,temporal3).equals("bad"))
		{
			verif = "false";
		}
		
		System.out.println("planeta del alien: ");
		ask = sc.nextLine();
		String temporal4 = ask;
		
		System.out.println("edad del alien: (en años alienigenas)");
		ask = sc.nextLine();
		float temporal5 = Float.valueOf(ask)*8; //a años humanos
		
		System.out.println("altura del alien: (en cm) ");
		ask = sc.nextLine();
		float temporal6 = Float.valueOf(ask)/100; // a metros
		
		System.out.println("peso del alien (en gramos)");
		ask = sc.nextLine();
		float temporal7 = Float.valueOf(ask)/1000; //a Kg
		
		System.out.println("antes de pasar al 8 pregunto por verif "+ verif);
		
		System.out.println("tipo del alien (I,V ó F)");
		
		ask = sc.nextLine();
		String temporal8 = ask.toUpperCase(); //CAPS LOCK
		System.out.println(ask+" "+ temporal8); //comparar antes y despues el input n° 8
		if ((!temporal8.equals("I")) && (!temporal8.equals("V")) && (!temporal8.equals("F"))) //if not equals to I and not equals to V and not equals to F
		{
			verif = "false";
		}
		
		if(verif == "true")
		{
			System.out.println("Ingreso dentro de lo correcto");
			insertarElementoSTR(nEsp,temporal1);
			insertarElementoSTR(name,temporal2);
			insertarElementoSTR(alienID,temporal3);
			insertarElementoSTR(planet,temporal4);
			insertarElementoFloat(age,temporal5); //FloatvalueOf, aca usé Float.valueOf() porque si cambio de nextline a nextfloat el scanner no lee inputs
			insertarElementoFloat(height,temporal6); //FloatvalueOf. así que mejor nextline a todo y cambiar a float si se requiere
			insertarElementoFloat(weight,temporal7); //FloatvalueOf. float en todo porque con tanta division quedaran decimales
			insertarElementoSTR(tipo,temporal8);
			
		}
		else if (verif == "false") //si alguno de ellos falla..
		{
			System.out.println("Ha ingresado erroneamente alguno de los datos, este alien no será ingresado");
		
		}
	}

	public static void insertarElementoSTR(String vector[],String insert) // string[] is filled with null, number[] is filled with zero
		{
			int size = vector.length;
			for(int i = 0; i < size;i++) 
			{
				if (vector[i] == (null))
				{
					vector[i] = insert;
					return;
				}	
			}
		}
		
	public static void insertarElementoFloat(Float vector[],float insert ) //la unica diferencia es que el insert es float
		{
			int size = vector.length;
			for(int i = 0; i < size;i++) 
			{
				if(vector[i] == null) //resulta que un vector de Floats se llena con nulls al darle tamaño
				{
					vector[i] = insert; //, con esto pongo la variable nueva al final del vector
					return;
				}	
			}
		}
		
	public static String verificarDigitos(int digito_esperado,String digit_entregado)
		{
			String [] array = digit_entregado.split("");
			String result = "";
			int digitos_reales = array.length;
			
			if(digito_esperado == digitos_reales)
			{
				result = "good";
			}
			else if(digito_esperado != digitos_reales)
			{
				result = "bad";
			}
			return result;
		}
		
	public static int lastUsed(String[] LIST) //any list will do the trick, as all lists are made at the same rhythm so i only need to check ID once
		{
			int lastIDx = -1;
			
			for(int i = 0; i< LIST.length;i++) 
			{
				if(LIST[i] == null) 
				{	
					lastIDx = i;
					return lastIDx;
				}
			}
			System.out.println("not found or something happened...?");
			return -1; //just in case
		}
	
	public static void modifyALIEN(String[] nEsp, String[] name, String[] alienID, String[] planet, Float[] age, Float[] height, Float[] weight, String[] tipo) 
		{	//punto2: ↓
			//pregunto por el ID del alien, consigo su coordenada, imprimo toda la info 
			Scanner sc = new Scanner(System.in);
			System.out.println("2) Modificar un extraterrestre (SI/NO)");
			String askk = sc.nextLine().toUpperCase();
	
			if (askk.equals("NO"))
			{	
				System.out.println("...closing (2)");
				return;
			}
			else //anything else will continue to ask for modifications for certain alien
			{	//compare asked ID with existing ID, if true then continue, else close
				System.out.println("Ingrese ID para el alien que desea modificar (8 digitos)");
				String aID = sc.nextLine().toUpperCase(); //ask for id
				int lastID = lastUsed(nEsp); //for loop 
				for(int i = 0; i< lastID ;i++) 
				{
					if (alienID[i].equals(aID))
					{
						System.out.println("This alien exists!"); //using aID from now on
						
						int indexedAlien = getIndex(alienID, aID); //from this index change everything about selected alien
						
						System.out.println("Edite especie");
						nEsp[indexedAlien] = sc.nextLine();
						
						System.out.println("Edite nombre");
						name[indexedAlien] = sc.nextLine();
						
						System.out.println("Edite id alien");
						alienID[indexedAlien] = sc.nextLine();
						
						System.out.println("Edite planeta");
						planet[indexedAlien] = sc.nextLine();
						
						System.out.println("Edite edad alienigena");
						age[indexedAlien] = Float.valueOf(sc.nextLine());
						
						System.out.println("Edite altura del alien");
						height[indexedAlien] = Float.valueOf(sc.nextLine());
						
						System.out.println("Edite peso en gramos");
						weight[indexedAlien] = Float.valueOf(sc.nextLine());
						
						System.out.println("Edite tipo de alien");
						tipo[indexedAlien] = sc.nextLine();
						
						break; //after being finished stop the loop, no need for it anymore
					}
					
				}
				
			}
		
		}
	
	//punto 6:eliminar etx: a partir del ID alien,a ver si no explota el java por hacer esto: vector[coord_a_eliminar] = null,
	//y luego if null empujar a la derecha
	
	
	
	
	
	public static void showByPlanet(String[] nEsp, String[] name, String[] alienID, String[] planet, Float[] age, Float[] height, Float[] weight, String[] tipo, String humanN[],String [] humanPlanet) 
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.println("9) Mostrar por planeta (SI/NO)");
		String ask = sc.nextLine().toUpperCase();
		
		if (ask.equals("NO")) 
		{
			System.out.println("cerrando (9)");
		}
		
		else if (ask.equals("SI")) 
		{
			System.out.println("Ingrese planeta");
			String aPlanet = sc.nextLine();
			int lastID = lastUsed(nEsp);
			int cont = 0;

			for(int i = 0;i < lastID; i++) 
			{
				if(planet[i].equals(aPlanet)) //if true then it exists
				{

					int indexedPlanet = getIndex(planet,aPlanet); // (list,element) return is index 
					//    ↑ is inside of the loop so it should change in order to print the rest of aliens who share the same planet
					
					System.out.println("En el planeta "+aPlanet+":");
					
					
					
					System.out.println(nEsp[indexedPlanet]);
					System.out.println(name[indexedPlanet]);
					System.out.println(alienID[indexedPlanet]);
					System.out.println(age[indexedPlanet]);
					System.out.println(height[indexedPlanet]);
					System.out.println(weight[indexedPlanet]);
					System.out.println(tipo[indexedPlanet]);
					
					
					System.out.println("\n"); //space space space
					
					
				}
				
				for(int k = 0; k < humanPlanet.length; k++) //each k is a different human
				{
				String [] partesHumanP = humanPlanet[k].split("/");
				
				int cantidad_partesH = (partesHumanP.length);
				
				
				
				
				for(int iter = 0; iter<cantidad_partesH; iter++)
					{
						if(partesHumanP[iter].equals(aPlanet)) //iter checks every planet of every human
							{//and if any of them is the same as the input

								cont += 1; //count him
							}
					
					}
				}
			if (cont == 1)
			{
				System.out.println("Hay "+cont+" humano en el planeta "+aPlanet);
			}
			else if (cont > 1) 
			{
				System.out.println("Hay "+cont+" humanos en el planeta "+aPlanet);
			}
			
			
			
			
			
		}
		
		
	
	}
	
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	public static void showbyAlienID(String [] alienID) //8
	{	
		Scanner sc = new Scanner(System.in);
		
		System.out.println("8) Buscar por alien ID (SI/NO)");
		
		String ask = sc.nextLine().toUpperCase();
		if (ask.equals("NO")) 
		{	
			System.out.println("cerrando (8)");
		}	
		else 
		{	
			String verif = "false";
			System.out.println("Ingrese alien ID");
			String aID = sc.nextLine(); //ask for ID
			for (int i = 0 ; i < alienID.length; i++) 
			{	
				if (aID.equals(alienID[i])) //if id is found 
				{	
					System.out.println("Alien encontrado");
					verif = "true";
					
					//System.out.println(//imprimir alien name[i]);
					break;
				}
				else 
				{
					verif = "false";
				}
				

			}	
			if (verif.equals("false"))
			{
				System.out.println("No se ha encontrado alien");
			}
			
			
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void showByType(String [] tipo) //11
	{	
		Scanner sc = new Scanner(System.in);
		
		float c_V = 0; //amount of vertebrate type alien
		float c_I = 0; //amount of invertebrate type alien
		float c_F = 0; //amount of flexible type alien
		
		System.out.println("11) Mostrar tabla (SI/NO)");
		String ask = sc.nextLine().toUpperCase();
		if (ask.equals("NO")) 
		{
			System.out.println("Cerrando (11)");
		}
		else 
			{
			for(int i = 0; i < tipo.length; i++) //using alien type list to check every 
			{	
				if(tipo[i].equals("V")) { c_V += 1;}
				if(tipo[i].equals("F")) { c_F += 1;}
				if(tipo[i].equals("I")) { c_I += 1;}
			}	
			
			float total = c_V+c_I+c_F;
			
			
			
			System.out.println("Cantidad de alien de tipo vertebrado: "+ c_V); //show amout of type
			System.out.println("Porcentaje vertebrado: "+(c_V/total)*100+ " %");
			System.out.println();
			System.out.println("Cantidad de alien de tipo invertebrado: "+ c_I);
			System.out.println("Porcentaje invertebrado: "+(c_I/total)*100+ " %");
			System.out.println();
			System.out.println("Cantidad de alien de tipo flexible: "+ c_F);
			System.out.println("Porcentaje flexible: "+(c_F/total)*100+ " %");
			
			System.out.println("Cantidad total de alien: " + total); //total amount
			}
		
	}
	
	
	
	
	public static void eliminate(String [] list , String element) //list and element, search for element in list, set value to null and push
	{
		for (int i = 0; i< list.length;i++) 
		{
			if (list[i].equals(element)) 
			{//element to eliminate is found, right now i is the element id
				list[i] = null; //set element value to null
				break; //STOP
			}
			
		}
		String aux = "";
		for(int a = 0; a < list.length-1;a++) {
			for(int b = a+1; b< list.length; b++) {
				if (list[a] == null) //if null then move 
				{
					aux = list[a]; //save first
					list[a] = list[b]; //first moves second
					list[b] = aux; //second become saved first
					
				}
			}
		}
		
	}
	
	public static void eliminateFloat(Float [] list , float element) //list and element, search for element in list, set value to null and push
	{
		for (int i = 0; i< list.length;i++) 
		{
			if (list[i] == (element)) 
			{//element to eliminate is found, right now i is the element id
				list[i] = null; //set element value to null
				break; //STOP
			}
			
		}
		float aux = 0;
		for(int a = 0; a < list.length-1;a++) {
			for(int b = a+1; b< list.length; b++) {
				if (list[a] == null) //if null then move 
				{
					aux = list[a]; //save first
					list[a] = list[b]; //first moves second
					list[b] = aux; //second become saved first
					
				}
			}
		}
		
	}
	
	
	
	
	
	
	public static void alienElimination(String[] nEsp, String[] name, String[] alienid, String[] planet, Float[] age, Float[] height, Float[] weight, String[] tipo) //6
	{//from an ID input checks if correct then using index method to activate elimination method to all lists related to that alien
		Scanner sc = new Scanner(System.in);
		System.out.println("6) Ingrese id universal (SI/NO)");
		String ask = sc.nextLine().toUpperCase();
		if (ask.equals("NO")) 
		{
			System.out.println("cerrando (6)");
			
		}
		else 
		{
			System.out.println("Ingrese identificador universal del alien que desea eliminar");
			
			String aID = sc.nextLine();
			
			String verif = "";
			int indexarID = 0;
			
			for(int i = 0;i < alienid.length; i++) 
			{	//ask for aID in list
				if(alienid[i].equals(aID))  
				{//if true then save value
					System.out.println("Alien encontrado!");
					indexarID = i; //this coordinate references to the alien data
					verif = "true";
					break;
				}
			}
			
			if (verif == "true" )
			{ //if true then alienId exists and we have the coordinate
				//proceed to eliminate alien related data
				eliminate(nEsp,nEsp[indexarID]);
				eliminate(name,name[indexarID]);
				eliminate(alienid,alienid[indexarID]);
				eliminate(planet,planet[indexarID]);
				eliminateFloat(age,age[indexarID]); //these are float
				eliminateFloat(height,height[indexarID]);
				eliminateFloat(weight,weight[indexarID]);
				eliminate(tipo,tipo[indexarID]);

			}
			
			
		}
		
		
	}
	
}
