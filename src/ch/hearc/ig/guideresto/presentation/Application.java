/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.presentation;

import ch.hearc.ig.guideresto.business.BasicEvaluation;
import ch.hearc.ig.guideresto.business.City;
import ch.hearc.ig.guideresto.business.CompleteEvaluation;
import ch.hearc.ig.guideresto.business.Evaluation;
import ch.hearc.ig.guideresto.business.EvaluationCriteria;
import ch.hearc.ig.guideresto.business.Grade;
import ch.hearc.ig.guideresto.business.Restaurant;
import ch.hearc.ig.guideresto.business.RestaurantType;
import ch.hearc.ig.guideresto.persistence.mock.DbConnection;
import ch.hearc.ig.guideresto.persistence.mock.FakeItems;
import ch.hearc.ig.guideresto.persistence.mock.MapperCity;
import ch.hearc.ig.guideresto.persistence.mock.MapperRestaurant;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author julien.plumez
 */
public class Application {
    
    private static Scanner scanner;
    
    public static void main(String[] args) {
        
        DbConnection driver = new DbConnection();
        Connection cnn = driver.getConnection();
        
        scanner = new Scanner(System.in);
        
        System.out.println("Bienvenue dans GuideResto ! Que souhaitez-vous faire ?");
        int choice;
        do{
            printMainMenu();
            choice = readInt();
            proceedMainMenu(choice);
        }while(choice != 0);
    }
    
    /**
     * Affichage du menu principal de l'application
     */
    private static void printMainMenu(){
        System.out.println("======================================================");
        System.out.println("Que voulez-vous faire ?");
        System.out.println("1. Afficher la liste de tous les restaurants");
        System.out.println("2. Rechercher un restaurant par son nom");
        System.out.println("3. Rechercher un restaurant par ville");
        System.out.println("4. Rechercher un restaurant par son type de cuisine");
        System.out.println("5. Saisir un nouveau restaurant");
        System.out.println("0. Quitter l'application");
    }
    
    /**
     * On gère le choix saisi par l'utilisateur
     * @param choice Un nombre entre 0 et 5.
     */
    private static void proceedMainMenu(int choice){
        switch(choice){
            case 1:
                showRestaurantsList();
                break;
            case 2:
                searchRestaurantByName();
                break;
            case 3:
                searchRestaurantByCity();
                break;
            case 4:
                searchRestaurantByType();
                break;
            case 5:
                addNewRestaurant();
                break;
            case 0:
                System.out.println("Au revoir !");
                break;
            default :
                System.out.println("Erreur : saisie incorrecte. Veuillez réessayer");
                break;
        }
    }
    
    /**
     * On affiche à l'utilisateur une liste de restaurants numérotés, et il doit en sélectionner un !
     * @param restaurants Liste à afficher
     * @return L'instance du restaurant choisi par l'utilisateur
     */
    private static Restaurant pickRestaurant(Set<Restaurant> restaurants){
        if(restaurants.isEmpty()){ // Si la liste est vide on s'arrête là
            System.out.println("Aucun restaurant n'a été trouvé !");
            return null;
        }
        
        String result;
        for(Restaurant currentRest : restaurants){
            result = "";
            result = "\"" + result + currentRest.getName() + "\" - " + currentRest.getAddress().getStreet() + " - ";
            result = result + currentRest.getAddress().getCity().getZipCode() + " " + currentRest.getAddress().getCity().getCityName();
            System.out.println(result);
        }
        
        System.out.println("Veuillez saisir le nom exact du restaurant dont vous voulez voir le détail, ou appuyez sur Enter pour revenir en arrière");
        String choice = readString();
        
        return searchRestaurantByName(restaurants, choice);
    }
    
    /**
     * Affiche la liste de tous les restaurants, sans filtre
     */
    private static void showRestaurantsList(){
        System.out.println("Liste des restaurants : ");
        MapperRestaurant maprest = new MapperRestaurant();
        
        Restaurant restaurant = pickRestaurant(/*FakeItems.getAllRestaurants()*/maprest.find(new DbConnection().getConnection()));
        
        if(restaurant != null){ // Si l'utilisateur a choisi un restaurant, on l'affiche, sinon on ne fait rien et l'application va réafficher le menu principal
            showRestaurant(restaurant);
        }
    }
    
    /**
     * Affiche une liste de restaurants dont le nom contient une chaîne de caractères saisie par l'utilisateur
     */
    private static void searchRestaurantByName(){
        System.out.println("Veuillez entrer une partie du nom recherché : ");
        String research = readString();
        
        // Comme on ne peut pas faire de requête SQL avec la classe FakeItems, on trie les données manuellement.
        // Il est évident qu'une fois que vous utiliserez une base de données, il ne faut PAS garder ce système.
        Set<Restaurant> fullList = FakeItems.getAllRestaurants();
        Set<Restaurant> filteredList = new LinkedHashSet();
        
        for(Restaurant currentRestaurant : fullList){ // On parcourt la liste complète et on ajoute les restaurants correspondants à la liste filtrée.
            if(currentRestaurant.getName().toUpperCase().indexOf(research.toUpperCase()) >= 0){ // On met tout en majuscules pour ne pas tenir compte de la casse
                filteredList.add(currentRestaurant);
            }
        }
        
        Restaurant restaurant = pickRestaurant(filteredList);
        
        if(restaurant != null){
            showRestaurant(restaurant);
        }
    }
    
    /**
     * Affiche une liste de restaurants dont le nom de la ville contient une chaîne de caractères saisie par l'utilisateur
     */
    private static void searchRestaurantByCity(){
        System.out.println("Veuillez entrer une partie du nom de la ville désirée : ");
        String research = readString();
        
        // Comme on ne peut pas faire de requête SQL avec la classe FakeItems, on trie les données manuellement.
        // Il est évident qu'une fois que vous utiliserez une base de données, il ne faut PAS garder ce système.
        Set<Restaurant> fullList = FakeItems.getAllRestaurants();
        Set<Restaurant> filteredList = new LinkedHashSet();
        
        for(Restaurant currentRestaurant : fullList){ // On parcourt la liste complète et on ajoute les restaurants correspondants à la liste filtrée.
            if(currentRestaurant.getAddress().getCity().getCityName().toUpperCase().indexOf(research.toUpperCase()) >= 0){ // On met tout en majuscules pour ne pas tenir compte de la casse
                filteredList.add(currentRestaurant);
            }
        }
        
        Restaurant restaurant = pickRestaurant(filteredList);
        
        if(restaurant != null){
            showRestaurant(restaurant);
        }
    }
    
    /**
     * L'utilisateur choisit une ville parmi celles présentes dans le système.
     * @param cities La liste des villes à présnter à l'utilisateur
     * @return La ville sélectionnée, ou null si aucune ville n'a été choisie.
     */
    private static City pickCity(){
        System.out.println("Voici la liste des villes possibles, veuillez entrer le NPA de la ville désirée : ");
        MapperCity mapCity = new MapperCity();
        Connection cnn = new DbConnection().getConnection();
        
        for(City currentCity : mapCity.find(cnn)){
            System.out.println(currentCity.getZipCode()+ " " + currentCity.getCityName());
        }
        System.out.println("Entrez \"NEW\" pour créer une nouvelle ville");
        String choice = readString();
        
        if(choice.equals("NEW")){
            City city = new City();
            city.setId(1); // A modifier quand on a la connexion avec la BDD.
            System.out.println("Veuillez entrer le NPA de la nouvelle ville : ");
            city.setZipCode(readString());
            System.out.println("Veuillez entrer le nom de la nouvelle ville : ");
            city.setCityName(readString());
            mapCity.insert(city, cnn);
            return city;
        }
        
        return mapCity.findById(choice, cnn);
    }
    
    /**
     * L'utilisateur choisit un type de restaurant parmis ceux présents dans le système.
     * @param types La liste des types de restaurant à présnter à l'utilisateur
     * @return Le type sélectionné, ou null si aucun type n'a été choisi.
     */
    private static RestaurantType pickRestaurantType(Set<RestaurantType> types){
        System.out.println("Voici la liste des types possibles, veuillez entrer le libellé exact du type désiré : ");
        
        for(RestaurantType currentType : types){
            System.out.println("\"" + currentType.getLabel() + "\" : " + currentType.getDescription());
        }
        String choice = readString();
        
        return searchTypeByLabel(types, choice);
    }
    
    /**
     * L'utilisateur commence par sélectionner un type de restaurant, puis sélectionne un des restaurants proposés s'il y en a.
     * Si l'utilisateur sélectionne un restaurant, ce dernier lui sera affiché.
     */
    private static void searchRestaurantByType(){
        // Comme on ne peut pas faire de requête SQL avec la classe FakeItems, on trie les données manuellement.
        // Il est évident qu'une fois que vous utiliserez une base de données, il ne faut PAS garder ce système.
        Set<Restaurant> fullList = FakeItems.getAllRestaurants();
        Set<Restaurant> filteredList = new LinkedHashSet();
        
        RestaurantType chosenType = pickRestaurantType(FakeItems.getRestaurantTypes());
        
        if(chosenType != null){ // Si l'utilisateur a sélectionné un type, sinon on ne fait rien et la liste sera vide.
            for(Restaurant currentRestaurant : fullList){
                if(currentRestaurant.getType() == chosenType){
                    filteredList.add(currentRestaurant);
                }
            }
        }
        
        Restaurant restaurant = pickRestaurant(filteredList);
        
        if(restaurant != null){
            showRestaurant(restaurant);
        }
    }
    
    /**
     * Le programme demande les informations nécessaires à l'utilisateur puis crée un nouveau restaurant dans le système.
     */
    private static void addNewRestaurant(){
        System.out.println("Vous allez ajouter un nouveau restaurant !");
        System.out.println("Quel est son nom ?");
        String name = readString();
        System.out.println("Veuillez entrer une courte description : ");
        String description = readString();
        System.out.println("Veuillez entrer l'adresse de son site internet : ");
        String website = readString();
        System.out.println("Rue : ");
        String street = readString();
        City city;
        do{ // La sélection d'une ville est obligatoire, donc l'opération se répètera tant qu'aucune ville n'est sélectionnée.
            city = pickCity();
        }while(city == null);
        RestaurantType restaurantType = null;
        do{ // La sélection d'un type est obligatoire, donc l'opération se répètera tant qu'aucun type n'est sélectionné.
            restaurantType = pickRestaurantType(FakeItems.getRestaurantTypes());
        }while(restaurantType == null);
        
        Restaurant restaurant = new Restaurant(1, name, description, website, street, city, restaurantType);
        city.getRestaurants().add(restaurant);
        restaurantType.getRestaurants().add(restaurant);
        FakeItems.getAllRestaurants().add(restaurant);
        
        showRestaurant(restaurant);
    }
    
    /**
     * Affiche toutes les informations du restaurant passé en paramètre, puis affiche le menu des actions disponibles sur ledit restaurant
     * @param restaurant Le restaurant à afficher
     */
    private static void showRestaurant(Restaurant restaurant){
        System.out.println("Affichage d'un restaurant : ");
        StringBuilder sb = new StringBuilder();
        sb.append(restaurant.getName()).append("\n");
        sb.append(restaurant.getDescription()).append("\n");
        sb.append(restaurant.getType().getLabel()).append("\n");
        sb.append(restaurant.getWebsite()).append("\n");
        sb.append(restaurant.getAddress().getStreet()).append(", ");
        sb.append(restaurant.getAddress().getCity().getZipCode()).append(" ").append(restaurant.getAddress().getCity().getCityName()).append("\n");
        sb.append("Nombre de likes : ").append(countLikes(restaurant.getEvaluations(), true)).append("\n");
        sb.append("Nombre de dislikes : ").append(countLikes(restaurant.getEvaluations(), false)).append("\n");
        sb.append("\nEvaluations reçues : ").append("\n");
        
        String text;
        for(Evaluation currentEval : restaurant.getEvaluations()){
            text = getCompleteEvaluationDescription(currentEval);
            if(text != null){ // On va recevoir des null pour les BasicEvaluation donc on ne les traite pas !
                sb.append(text).append("\n");
            }
        }
        
        System.out.println(sb.toString());
        
        int choice;
        do{ // Tant que l'utilisateur n'entre pas 0 ou 6, on lui propose à nouveau les actions
            showRestaurantMenu();
            choice = readInt();
            proceedRestaurantMenu(choice, restaurant);
        }while(choice != 0 && choice != 6); // 6 car le restaurant est alors supprimé...
    }
    
    /**
     * Parcourt la liste et compte le nombre d'évaluations basiques positives ou négatives en fonction du paramètre likeRestaurant
     * @param evaluations La liste des évaluations à parcourir
     * @param likeRestaurant Veut-on le nombre d'évaluations positives ou négatives ?
     * @return Le nombre d'évaluations positives ou négatives trouvées
     */
    private static int countLikes(Set<Evaluation> evaluations, Boolean likeRestaurant){
        int count = 0;
        for(Evaluation currentEval : evaluations){
            if(currentEval instanceof BasicEvaluation && ((BasicEvaluation)currentEval).getLikeRestaurant() == likeRestaurant){
                count ++;
            }
        }
        return count;
    }
    
    /**
     * Retourne un String qui contient le détail complet d'une évaluation si elle est de type "CompleteEvaluation". Retourne null s'il s'agit d'une BasicEvaluation
     * @param eval L'évaluation à afficher
     * @return Un String qui contient le détail complet d'une CompleteEvaluation, ou null s'il s'agit d'une BasicEvaluation
     */
    private static String getCompleteEvaluationDescription(Evaluation eval){
        String result = null;
        
        if(eval instanceof CompleteEvaluation){
            CompleteEvaluation ce = (CompleteEvaluation) eval;
            result = "";
            result += "Evaluation de : " + ce.getUsername() + "\n";
            result += "Commentaire : " + ce.getComment() + "\n";
            for(Grade currentGrade : ce.getGrades()){
                result += currentGrade.getCriteria().getName() + " : " + currentGrade.getGrade() + "/5" + "\n";
            }
        }
        
        return result;
    }
    
    /**
     * Affiche dans la console un ensemble d'actions réalisables sur le restaurant actuellement sélectionné !
     */
    private static void showRestaurantMenu(){
        System.out.println("======================================================");
        System.out.println("Que souhaitez-vous faire ?");
        System.out.println("1. J'aime ce restaurant !");
        System.out.println("2. Je n'aime pas ce restaurant !");
        System.out.println("3. Faire une évaluation complète de ce restaurant !");
        System.out.println("4. Editer ce restaurant");
        System.out.println("5. Editer l'adresse du restaurant");
        System.out.println("6. Supprimer ce restaurant");
        System.out.println("0. Revenir au menu principal");
    }
    
    /**
     * Traite le choix saisi par l'utilisateur
     * @param choice Un numéro d'action, entre 0 et 6. Si le numéro ne se trouve pas dans cette plage, l'application ne fait rien et va réafficher le menu complet.
     * @param restaurant L'instance du restaurant sur lequel l'action doit être réalisée
     */
    private static void proceedRestaurantMenu(int choice, Restaurant restaurant){
        switch(choice){
            case 1 : 
                addBasicEvaluation(restaurant, true);
                break;
            case 2 : 
                addBasicEvaluation(restaurant, false);
                break;
            case 3 : 
                evaluateRestaurant(restaurant);
                break;
            case 4 : 
                editRestaurant(restaurant);
                break;
            case 5 :
                editRestaurantAddress(restaurant);
                break;
            case 6 : 
                deleteRestaurant(restaurant);
                break;
            case 0 : 
                break;
            default : break;
        }
    }
    
    /**
     * Ajoute au restaurant passé en paramètre un like ou un dislike, en fonction du second paramètre.
     * L'IP locale de l'utilisateur est enregistrée. S'il s'agissait d'une application web, il serait préférable de récupérer l'adresse IP publique de l'utilisateur.
     * @param restaurant Le restaurant qui est évalué
     * @param like Est-ce un like ou un dislike ?
     */
    private static void addBasicEvaluation(Restaurant restaurant, Boolean like){
        String ipAddress;
        try {
            ipAddress = Inet4Address.getLocalHost().toString(); // Permet de retrouver l'adresse IP locale de l'utilisateur.
        } catch (UnknownHostException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.INFO, "Error - Couldn't retreive host IP address");
            ipAddress = "Indisponible";
        }
        BasicEvaluation eval = new BasicEvaluation(1, new Date(), restaurant, like, ipAddress);
        restaurant.getEvaluations().add(eval);
        System.out.println("Votre vote a été pris en compte !");
    }
    
    /**
     * Crée une évaluation complète pour le restaurant. L'utilisateur doit saisir toutes les informations (dont un commentaire et quelques notes)
     * @param restaurant Le restaurant à évaluer
     */
    private static void evaluateRestaurant(Restaurant restaurant){
        System.out.println("Merci d'évaluer ce restaurant !");
        System.out.println("Quel est votre nom d'utilisateur ? ");
        String username = readString();
        System.out.println("Quel commentaire aimeriez-vous publier ?");
        String comment = readString();
        
        CompleteEvaluation eval = new CompleteEvaluation(1, new Date(), restaurant, comment, username);
        restaurant.getEvaluations().add(eval);
        
        Grade grade; // L'utilisateur va saisir une note pour chaque critère existant.
        System.out.println("Veuillez svp donner une note entre 1 et 5 pour chacun de ces critères : ");
        for(EvaluationCriteria currentCriteria : FakeItems.getEvaluationCriterias()){
            System.out.println(currentCriteria.getName() + " : " + currentCriteria.getDescription());
            Integer note = readInt();
            grade = new Grade(1, note, eval, currentCriteria);
            eval.getGrades().add(grade);
        }
        
        System.out.println("Votre évaluation a bien été enregistrée, merci !");
    }
    
    /**
     * Force l'utilisateur à saisir à nouveau toutes les informations du restaurant (sauf la clé primaire) pour le mettre à jour.
     * Par soucis de simplicité, l'utilisateur doit tout resaisir.
     * @param restaurant Le restaurant à modifier
     */
    private static void editRestaurant(Restaurant restaurant){
        System.out.println("Edition d'un restaurant !");
        
        System.out.println("Nouveau nom : ");
        restaurant.setName(readString());
        System.out.println("Nouvelle description : ");
        restaurant.setDescription(readString());
        System.out.println("Nouveau site web : ");
        restaurant.setWebsite(readString());
        System.out.println("Nouveau type de restaurant : ");
        
        RestaurantType newType = pickRestaurantType(FakeItems.getRestaurantTypes());
        if(newType != null && newType != restaurant.getType()){
            restaurant.getType().getRestaurants().remove(restaurant); // Il faut d'abord supprimer notre restaurant puisque le type va peut-être changer
            restaurant.setType(newType);
            newType.getRestaurants().add(restaurant);
        }
        
        System.out.println("Merci, le restaurant a bien été modifié !");
    }
    
    /**
     * Permet à l'utilisateur de mettre à jour l'adresse du restaurant.
     * Par soucis de simplicité, l'utilisateur doit tout resaisir.
     * @param restaurant Le restaurant dont l'adresse doit être mise à jour.
     */
    private static void editRestaurantAddress(Restaurant restaurant){
        System.out.println("Edition de l'adresse d'un restaurant !");
        
        System.out.println("Nouvelle rue : ");
        restaurant.getAddress().setStreet(readString());
        
        City newCity = pickCity();
        if (newCity != null && newCity != restaurant.getAddress().getCity()){
            restaurant.getAddress().getCity().getRestaurants().remove(restaurant); // On supprime l'adresse de la ville
            restaurant.getAddress().setCity(newCity);
            newCity.getRestaurants().add(restaurant);
        }
        
        System.out.println("L'adresse a bien été modifiée ! Merci !");
    }
    
    /**
     * Après confirmation par l'utilisateur, supprime complètement le restaurant et toutes ses évaluations du référentiel.
     * @param restaurant Le restaurant à supprimer.
     */
    private static void deleteRestaurant(Restaurant restaurant){
        System.out.println("Etes-vous sûr de vouloir supprimer ce restaurant ? (O/n)");
        String choice = readString();
        if(choice.equals("o") || choice.equals("O")){
            MapperRestaurant mapRest = new MapperRestaurant();
            mapRest.delete(restaurant, new DbConnection().getConnection());
            //FakeItems.getAllRestaurants().remove(restaurant);
            restaurant.getAddress().getCity().getRestaurants().remove(restaurant);
            restaurant.getType().getRestaurants().remove(restaurant);
            System.out.println("Le restaurant a bien été supprimé !");
        }
    }
    
    /**
     * Recherche dans le Set le restaurant comportant le nom passé en paramètre.
     * Retourne null si le restaurant n'est pas trouvé.
     * @param restaurants Set de restaurants
     * @param name Nom du restaurant à rechercher
     * @return L'instance du restaurant ou null si pas trouvé
     */
    private static Restaurant searchRestaurantByName(Set<Restaurant> restaurants, String name){
        for(Restaurant current : restaurants){
            if(current.getName().toUpperCase().equals(name.toUpperCase())){
                return current;
            }
        }
        return null;
    }
    
    /**
     * Recherche dans le Set la ville comportant le code NPA passé en paramètre.
     * Retourne null si la ville n'est pas trouvée
     * @param cities Set de villes
     * @param zipCode NPA de la ville à rechercher
     * @return L'instance de la ville ou null si pas trouvé
     */
    private static City searchCityByZipCode(Set<City> cities, String zipCode){
        for(City current : cities){
            if(current.getZipCode().toUpperCase().equals(zipCode.toUpperCase())){
                return current;
            }
        }
        return null;
    }
    
    /**
     * Recherche dans le Set le type comportant le libellé passé en paramètre.
     * Retourne null si aucun type n'est trouvé.
     * @param types Set de types de restaurant
     * @param label Libellé du type recherché
     * @return L'instance RestaurantType ou null si pas trouvé
     */
    private static RestaurantType searchTypeByLabel(Set<RestaurantType> types, String label){
        for(RestaurantType current : types){
            if(current.getLabel().toUpperCase().equals(label.toUpperCase())){
                return current;
            }
        }
        return null;
    }
    
    /**
     * readInt ne repositionne pas le scanner au début d'une ligne donc il faut le faire manuellement sinon 
     * des problèmes apparaissent quand on demande à l'utilisateur de saisir une chaîne de caractères.
     * @return Un nombre entier saisi par l'utilisateur au clavier
     */
    private static int readInt(){
        int i = 0;
        boolean success = false;
        do{ // Tant que l'utilisateur n'aura pas saisi un nombre entier, on va lui demander une nouvelle saisie
            try{
                i = scanner.nextInt(); 
                success = true;
            }catch(InputMismatchException e){
                System.out.println("Erreur ! Veuillez entrer un nombre entier s'il vous plaît !");
            }finally{
                scanner.nextLine();
            }
            
        }while(!success);
        
        return i;
    }
    
    /**
     * Méthode readString pour rester consistant avec readInt !
     * @return Une chaîne de caractères saisie par l'utilisateur au clavier
     */
    private static String readString(){
        return scanner.nextLine();
    }
    
}
