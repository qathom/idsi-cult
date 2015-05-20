package ch.unige.idsi.cultweb.model;

import java.util.ArrayList;
import java.util.List;

public class RecommendationManager {

	private List<Recommendation> fullList = new ArrayList<Recommendation>();

	public RecommendationManager() {

		// Cinémas

		// Cinéma Bio 72

		Recommendation r1 = new Recommendation(
				76,
				"Les expositions au Cinéma Bio",
				"http://www.cinema-bio.ch/FILMS/HorsSerie/ExpositionsCinema/ExpositionsCinema2014.htm");
		this.fullList.add(r1);

		// Cinéma Rialto Cornavin"

		Recommendation r2 = new Recommendation(436, "Le pathé pass",
				"http://www.pathe.ch/fr/cartes/pathepass");
		this.fullList.add(r2);

		Recommendation r3 = new Recommendation(436,
				"Pour les familles : Le Family Ticket",
				"http://pathe.ch/fr/offres-speciales/family_geneve");
		this.fullList.add(r3);

		Recommendation r4 = new Recommendation(436,
				"Pour les juniors : Le Family Ticket",
				"http://pathe.ch/fr/cartes/juniorclub");
		this.fullList.add(r4);

		Recommendation r5 = new Recommendation(436, "Offres Partenaires",
				"http://pathe.ch/fr/partenaires/avantages_geneve");
		this.fullList.add(r5);

		Recommendation r6 = new Recommendation(436,
				"Pour les enfants : La lanterne lanterne magique",
				"http://pathe.ch/fr/offres-speciales/lanterne-magique");
		this.fullList.add(r6);

		Recommendation r7 = new Recommendation(436,
				"Le Cinéma Rex est sur Facebook",
				"https://www.facebook.com/pathegeneve");
		this.fullList.add(r7);

		// Cinéma Titanium

		Recommendation r8 = new Recommendation(825,
				"La page Facebook de Cineman Romandie",
				"https://www.facebook.com/pages/Cineman-Romandie/115199188500877");
		this.fullList.add(r8);

		// Cinéma Broadway

		Recommendation r9 = new Recommendation(424,
				"La page Facebook de Cineman Romandie",
				"https://www.facebook.com/pages/Cineman-Romandie/115199188500877");
		this.fullList.add(r9);

		// Cinéma Central
		Recommendation r10 = new Recommendation(426, "La carte cinepass",
				"http://cinepass.ch/fr/la-carte");
		this.fullList.add(r10);

		// Cinéma Spoutnik

		Recommendation r11 = new Recommendation(162,
				"Les galeries du Cinéma Spoutnik",
				"http://www.spoutnik.info/galeries/");
		this.fullList.add(r11);

		// Cinéma City

		Recommendation r12 = new Recommendation(314, "La carte Cinepass",
				"http://cinepass.ch/fr/la-carte");
		this.fullList.add(r12);

		// Cinéma Forum

		Recommendation r13 = new Recommendation(697, "Cinéma Forum",
				"http://omg2.lol");
		this.fullList.add(r13);

		// Cinéma Scala

		Recommendation r14 = new Recommendation(315, "La carte Cinepass",
				"http://cinepass.ch/fr/la-carte");
		this.fullList.add(r14);

		// Cinéma Rex

		Recommendation r15 = new Recommendation(339, "Le Pathé Pass",
				"http://www.pathe.ch/fr/cartes/pathepass");
		this.fullList.add(r15);

		Recommendation r16 = new Recommendation(339,
				"Pour les familles : Le Family Ticket",
				"http://pathe.ch/fr/offres-speciales/family_geneve");
		this.fullList.add(r16);

		Recommendation r17 = new Recommendation(339,
				"Pour les juniors : Le Family Ticket",
				"http://pathe.ch/fr/cartes/juniorclub");
		this.fullList.add(r17);

		Recommendation r18 = new Recommendation(339, "Offres Partenaires",
				"http://pathe.ch/fr/partenaires/avantages_geneve");
		this.fullList.add(r18);

		Recommendation r19 = new Recommendation(339,
				"Pour les enfants : La lanterne lanterne magique",
				"http://pathe.ch/fr/offres-speciales/lanterne-magique");
		this.fullList.add(r19);

		Recommendation r20 = new Recommendation(339,
				"Le Cinéma Rex est sur Facebook",
				"https://www.facebook.com/pathegeneve");
		this.fullList.add(r20);

		// Cinéma Nord-Sud
		Recommendation r21 = new Recommendation(413, "Cinéma Nord-Sud",
				"http://cine.ch/cinema/nord-sud-geneve-258");
		this.fullList.add(r21);

		// Musées

		// Musée International de la Croix-Rouge et du Croissant Rouge
		Recommendation r22 = new Recommendation(432,
				"Le musée de la croix-rouge est sur Facebook",
				"https://www.facebook.com/redcrossmuseum");
		this.fullList.add(r22);

		// Musée de l'Ordre de Malte

		Recommendation r23 = new Recommendation(998,
				"Les livres sur l'Ordre de Malte",
				"http://www.smommuseum.ch/version2/");
		this.fullList.add(r23);

		// Musée d'art et d'histoire

		Recommendation r24 = new Recommendation(295,
				"Le Musée d'art et d'histoire est sur Facebook",
				"https://www.facebook.com/mahgeneve");
		this.fullList.add(r24);

		// Musée du Vieux Plainpalais

		Recommendation r25 = new Recommendation(647, "Autrefois Genève",
				"http://www.autrefoisgeneve.ch/video.php?id=11104");
		this.fullList.add(r25);

		// Musée historique de la Réformation - Musée Jean Jacques Rousseau

		Recommendation r26 = new Recommendation(292,
				"La Maison de Rousseau et de la Littérature",
				"http://www.espace-rousseau.ch/");
		this.fullList.add(r26);

		// Musée d'Histoire des Sciences

		Recommendation r27 = new Recommendation(642,
				"La buvette du Musée d'Histoire des Sciences",
				"http://www.labarje.ch/lieux/sciences");
		this.fullList.add(r27);

		Recommendation r28 = new Recommendation(642,
				"L'association « Bancs publics", "http://www.bancspublics.ch/");
		this.fullList.add(r28);

		Recommendation r29 = new Recommendation(642, "Le CERN",
				"http://home.web.cern.ch/fr");
		this.fullList.add(r29);

		Recommendation r30 = new Recommendation(642,
				"La Société Astronomique de Genève", "http://www.astro-ge.net/");
		this.fullList.add(r30);

		// Musée des Télécommunications

		Recommendation r31 = new Recommendation(636, "ICT Discovery",
				"http://www.itu.int/en/ictdiscovery/Pages/default.aspx");
		this.fullList.add(r31);

		// Musée de l'horlogerie et de l'émaillerie

		Recommendation r32 = new Recommendation(313,
				"La maison de l'horlogerie", "http://www.montres-geneve.info/");
		this.fullList.add(r32);

		// Musée du jouet ancien

		Recommendation r33 = new Recommendation(727,
				"Le magasin dee jouets Franz Carl Weber",
				"http://www.fcw.ch/fr/magasins/geneve/");
		this.fullList.add(r33);

		// Musée Militaire Genevois

		Recommendation r34 = new Recommendation(644,
				"La vente des matériels militaires",
				"http://www.automnales.ch/fr/pages/stock-armee-184");
		this.fullList.add(r34);

		// Musée des Suisses dans le Monde

		Recommendation r35 = new Recommendation(646,
				" Le passeport musées Suisses", "http://www.museumspass.ch/fr/");
		this.fullList.add(r35);

		// Institut et Musée Voltaire

		Recommendation r36 = new Recommendation(561,
				"Le site web de la société voltaire",
				"http://societe-voltaire.org/");
		this.fullList.add(r36);

		// Musée philatélique des Nations Unies

		Recommendation r37 = new Recommendation(434,
				"La page twitter du musée philatélique des Nations Unies",
				"https://twitter.com/musardage");
		this.fullList.add(r37);

		// Musée de Carouge

		Recommendation r38 = new Recommendation(73,
				"La page du musée de carouge",
				"http://www.carouge.ch/musee-de-carouge");
		this.fullList.add(r38);

		// MAMCO - Musée d'Art Moderne et Contemporain

		Recommendation r39 = new Recommendation(184,
				"Les Chroniques des auteurs",
				"http://www.mamco.ch/CHRONIQUE.html");
		this.fullList.add(r39);

		// Musée Ariana

		Recommendation r40 = new Recommendation(433,
				"La page Facebook du Musée Ariana",
				"https://www.facebook.com/museearianageneve?ref=ts");
		this.fullList.add(r40);

		// Musée Rath

		Recommendation r41 = new Recommendation(254, "La carte Expopass",
				"http://institutions.ville-geneve.ch/fr/mah/publics/adultes/expopass/");
		this.fullList.add(r41);

		// Musée d'ethnographie de Genève - MEG Carl-Vogt

		Recommendation r42 = new Recommendation(191, "Le magazine de MEG",
				"http://www.ville-ge.ch/meg/totem/totem68.pdf");
		this.fullList.add(r42);

		Recommendation r43 = new Recommendation(191,
				"L'eMEG, le catalogue électronique des expositions",
				"http://www.ville-ge.ch/meg/emeg/index.php");
		this.fullList.add(r43);

		Recommendation r44 = new Recommendation(
				191,
				"MEG Carl-Vogt est sur Facebook",
				"https://www.facebook.com/pages/Mus%C3%A9e-dethnographie-de-Gen%C3%A8ve-MEG/112778378744946?ref=ts");
		this.fullList.add(r44);

		Recommendation r45 = new Recommendation(191,
				"Le café du MEG Carl-Vogt est sur Facebook",
				"https://www.facebook.com/Lecafedumeg");
		this.fullList.add(r45);

		Recommendation r46 = new Recommendation(191,
				"La Société des amis du musée d'ethnographie de Genève",
				"http://www.sameg.ch/");
		this.fullList.add(r46);

		Recommendation r47 = new Recommendation(191,
				"La Société des amis du musée d'ethnographie de Genève",
				"http://www.sameg.ch/");
		this.fullList.add(r47);

		// Musée d'ethnographie de Genève - MEG Conches

		Recommendation r48 = new Recommendation(
				691,
				"MEG Conches est sur Facebook",
				"https://www.facebook.com/permalink.php?id=112778378744946&story_fbid=576711059018340");
		this.fullList.add(r48);

		// Musée International de l'Automobile
		Recommendation r49 = new Recommendation(643,
				"Le salon automobile de Genève", "http://www.salon-auto.ch/fr/");
		this.fullList.add(r49);

		// Musée Barbier Muller
		Recommendation r50 = new Recommendation(334,
				"Le musée Barbier Mueller est sur Facebook",
				"https://www.facebook.com/MuseeBarbierMueller");
		this.fullList.add(r50);
	}

	public List<Recommendation> get(int id) {

		List<Recommendation> list = new ArrayList<Recommendation>();

		for (Recommendation r : this.fullList) {
			if (r.getId() == id) {
				list.add(r);
			}
		}

		return list;
	}

}
