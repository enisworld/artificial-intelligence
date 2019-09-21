import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.TextArea;

public class Main {
	ArrayList<Items> esyalar = new ArrayList<Items>();
	ArrayList<Kromozom> kromozomlar = new ArrayList<Kromozom>();// parent kromozomlar�n listesi
	ArrayList<Kromozom> yeniNesilKromozomlar1 ;
	ArrayList<Kromozom> yeniNesilKromozomlar2 = new ArrayList<Kromozom>() ;// child kromozomar�n listesi
	ArrayList<Float> degerler = new ArrayList<Float>();
	int []yuzde = new int[100];
	double MAXVALUE = 0;
	int ort = 0;
	double MAXVALUE2 = 0;
	int jenerasyonSayisi=1000;
	Kromozom bestOne ;
	Kromozom bestOne2;
	String cozumyolu = " ";
	private int kromozomSayisi;
	private JFrame frame;
	//private JPanel panel;
	//private JButton buton;
	private JTextField esya_sayisi;
	private JTextField kapasite;
	private JTextField txtKromozomSayisi;
	TextArea textArea;
	private JTextField textField;
	private JTextField txtMaxDeger;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 508, 370);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 482, 320);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		esya_sayisi = new JTextField();
		esya_sayisi.setBounds(134, 46, 86, 20);
		panel.add(esya_sayisi);
		esya_sayisi.setColumns(10);
		
		JLabel lblEsyaSayisi = new JLabel("Esya sayisi");
		lblEsyaSayisi.setBounds(25, 49, 79, 14);
		panel.add(lblEsyaSayisi);
		
		JLabel lblCantaKapasitesi = new JLabel("Canta kapasitesi");
		lblCantaKapasitesi.setBounds(25, 90, 99, 14);
		panel.add(lblCantaKapasitesi);
		
		kapasite = new JTextField();
		kapasite.setBounds(134, 87, 86, 20);
		panel.add(kapasite);
		kapasite.setColumns(10);
		
		JButton btnHesapla = new JButton("Hesapla");
		btnHesapla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int esya = Integer.parseInt(esya_sayisi.getText());
				System.out.println("esya sayisi = " + esya);
				int kapasit = Integer.parseInt(kapasite.getText());
				System.out.println("kapasite = " + kapasit);
				kromozomSayisi = Integer.parseInt(txtKromozomSayisi.getText());
				enIyiyeYerAyir(esya);// sonucu hesaplamak i�in.
				//Esya_olustur(esya);
				int cvt=0;
				for(int r=0;r<100;r++)
				{
					//System.out.println(r + ". ad�m");
					kromozomlar.clear();
					yeniNesilKromozomlar2.clear();
					degerler.clear();
					MAXVALUE = 0;
					Esya_olustur(esya);
					//dnmEsyaOlustur();
					//dnmEsyaOlustur2();
					Kromozom_olustur(esya,kapasit);
					degerHesapla(kromozomlar,esya);
					jenerasyon(esya, kapasit);

				}
				 
				//System.out.println("��z�m y�zdesi = " + ort/100);
				txtMaxDeger.setText("" + MAXVALUE2);
				//textField.setText("%" + ort);
				textArea.setText(cozumyolu);
			}
		});
		btnHesapla.setBounds(105, 133, 89, 23);
		panel.add(btnHesapla);
		
		JLabel lblKromozomSayisi = new JLabel("Kromozom Sayisi");
		lblKromozomSayisi.setBounds(260, 49, 99, 14);
		panel.add(lblKromozomSayisi);
		
		txtKromozomSayisi = new JTextField();
		txtKromozomSayisi.setBounds(358, 46, 54, 20);
		panel.add(txtKromozomSayisi);
		txtKromozomSayisi.setColumns(10);
		
		JLabel lblSonuc = new JLabel("Maksimum De\u011Fer");
		lblSonuc.setBounds(47, 180, 102, 14);
		panel.add(lblSonuc);
		
		textArea = new TextArea();
		textArea.setBounds(47, 249, 210, 46);
		panel.add(textArea);
		
		JLabel lblzm = new JLabel("\u00C7\u00F6z\u00FCm Bulma Y\u00FCzdesi :");
		lblzm.setBounds(274, 177, 138, 20);
		panel.add(lblzm);
		
		textField = new JTextField();
		textField.setBounds(414, 177, 46, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		txtMaxDeger = new JTextField();
		txtMaxDeger.setBounds(159, 177, 86, 20);
		panel.add(txtMaxDeger);
		txtMaxDeger.setColumns(10);
		
		JLabel lblMaksimumDegerinBulunduu = new JLabel("Maksimum degerin bulundu\u011Fu kromozom");
		lblMaksimumDegerinBulunduu.setBounds(47, 229, 251, 14);
		panel.add(lblMaksimumDegerinBulunduu);
		
	}
	//Ad�m 1: rastgele e�yalar ve bu e�yalar�n de�erleri olu�turulup esyalar arraylistinde tutulmaktad�r.
	private void Esya_olustur(int esyaSayisi)
	{
		for(int i=0;i<esyaSayisi;i++)
		{
			Items itm = new Items();
			Random rnd = new Random();
			itm.setAg�rl�k(Math.abs((rnd.nextInt())%10)+1);
			itm.setDeger(Math.abs(rnd.nextInt())%50+1);
			esyalar.add(itm);
		}
		for(int j=0;j<esyaSayisi;j++)
		{
			System.out.println(j + ". eleman ag�rl�k = " + esyalar.get(j).getAg�rl�k()
					+ " deger = " + esyalar.get(j).getDeger());
		}
	}
	//Ad�m 2: girilen �anta kapasitesini a�mayacak �ekilde rasgele kromozomlar �retmek i�in kulan�lmaktad�r.
	private void Kromozom_olustur(int esya,int kapasit)
	{
		for(int j=0;j<kromozomSayisi;j++)
		{
			Kromozom krom = new Kromozom();
			krom.getKromozom(esya);
			kromozomlar.add(krom);
			
			boolean kosul = true;//bu de�i�ken kromozomun a��rl��� �anta kapasitesini a�t�g� anda FALSE olup yeniden kromozom �retmek i�in kulan�lm��t�r.
			//�anta kapasitesini a�mayacak �ekilde kromozomlar �retir.
			while(kosul)
			{
				int kromozomAg�rl�g� = 0;
				//System.out.println("while dongusu giris");
				for(int i=0;i<esya;i++)
				{ 
					Random rnd = new Random();
					kromozomlar.get(j).kromozom[i] = Math.abs(rnd.nextInt())%2;
					//kromozomAg�rl�g� = kromozomAg�rl�g� + (kromozomlar.get(j).kromozom[i])*(esyalar.get(i).getAg�rl�k());
				}
				kromozomAg�rl�g� = ag�rl�kHesapla(j, kromozomlar, esya);
				if((kromozomAg�rl�g� <= kapasit)&&(kromozomAg�rl�g� != 0))
				{
					//System.out.println("ag�rl�k = " + kromozomAg�rl�g�);
					kosul = false;
				}
			}			
		}
	}
	// Bu fonksiyon girdi olarak verilen kromozom listesinin her bir kromozomunun degerini hasaplay�p degerler listesine ekliyor.  
	private void degerHesapla(ArrayList<Kromozom> arr3,int esya)
	{
		for(int i=0;i<kromozomSayisi;i++)
		{
			float top_deger=0;
			for(int j=0;j<esya;j++)
			{
				top_deger =top_deger + arr3.get(i).kromozom[j]*esyalar.get(j).getDeger();
			}
			degerler.add(top_deger);
		}
	}
	// bu fonksiyon girdi olarak verilen iki indisin kromozomlar�n�n
	// yar�s�n� kar��l�kl� de�i�tirme i�lemini yapmaktad�r.
	// indis1'in ilk yar�s� ile indis2'nin ilk yar�s�n�n yeri de�i�tiriliyor.
	private void crossover(int indis1,int indis2,int esya,int cantaKapasitesi)
	{
		int tmp;
		yeniNesilKromozomlar1 = new ArrayList<Kromozom>();
		yeniNesilKromozomlar1.clear();
		yeniNesilKromozomlar1 = arrCopy(kromozomlar, yeniNesilKromozomlar1, esya);
		for(int i=0;i<esya/2;i++)
		{
			tmp = yeniNesilKromozomlar1.get(indis1).kromozom[i];
			yeniNesilKromozomlar1.get(indis1).kromozom[i] = yeniNesilKromozomlar1.get(indis2).kromozom[i]; 
			yeniNesilKromozomlar1.get(indis2).kromozom[i] = tmp;
		}
		mutasyon(indis1,esya,yeniNesilKromozomlar1);
		mutasyon(indis2,esya,yeniNesilKromozomlar1);
		
		int count = ag�rl�kHesapla(indis1, yeniNesilKromozomlar1, esya);
		int count2= ag�rl�kHesapla(indis2, yeniNesilKromozomlar1, esya);
		if(((count <= cantaKapasitesi)&&(count2<=cantaKapasitesi))&&((count!=0)&&(count2!=0)))
		{
			yeniNesilKromozomlar2.add(yeniNesilKromozomlar1.get(indis1));
			yeniNesilKromozomlar2.add(yeniNesilKromozomlar1.get(indis2));
		}		
	}
	// girdi olarak verilen indisin i�erisideki bir g�r�n rastgele se�ilip
	// de�i�tirilmesidir. e�er 0 ise 1, 1 ise 0 yap�lmaktad�r.
	private void mutasyon(int indis12, int esya,ArrayList<Kromozom> array)
	{
		Random rnd = new Random();
		int b = Math.abs((rnd.nextInt())%esya);
		//System.out.println(b+". goz �nce = "+yeniNesilKromozomlar1.get(indis12).kromozom[b]);
		int cout=(array.get(indis12).kromozom[b] + 1) % 2;
		array.get(indis12).kromozom[b] = cout;
		//System.out.println("b="+b);
		//System.out.println(b + ". goz sonra = "+yeniNesilKromozomlar1.get(indis12).kromozom[b]);

		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// bu fonksiyona gelen indis kromozomunun a��rl��� hesaplan�r.
	private int ag�rl�kHesapla(int indis, ArrayList<Kromozom> arr,int esya)
	{
		int kromozomAg�rl�k = 0;
		for(int i=0;i<esya;i++)
		{
			kromozomAg�rl�k = kromozomAg�rl�k + arr.get(indis).kromozom[i]*esyalar.get(i).getAg�rl�k();
		}
		return kromozomAg�rl�k;
	}
	// kromozomlar listesindeki elemanla�n se�ilme y�zdelerini bulmak
	// i�in kullan�lan bir fonksiyondur.bu fonksiyon ile her bir kromozomun
	//se�ilme y�zdesi hesaplan�p 100 elemanl� bir dizide y�zdesi kadar eleman 
	// etiketlenerek yaz�lm��t�r. 1. kromozomun y�zdesi kadar eleman 
	// 1 olarak etiketlenmi�tir.,2. kromozomun 2 �eklinde y�zde[100] dizisi olu�turulmu�tur.
	private void uygunlukFonksiyonu(ArrayList<Kromozom> arr1,int esya, int cantaKapasitesi)
	{
		double [] kromozomY = new double[kromozomSayisi];
		double a,b,tp=0.0;
		
		for(int i=0;i<kromozomSayisi;i++)
		{
			a= Double.parseDouble(Integer.toString(cantaKapasitesi));
			int ag�rl�k = ag�rl�kHesapla(i, arr1, esya);
			b=Double.parseDouble(Integer.toString(ag�rl�k));
			double num =(a/b)*(degerler.get(i));
			BigDecimal big = new BigDecimal(num).setScale(0, BigDecimal.ROUND_HALF_UP);
			num = big.doubleValue();
			kromozomY[i] = num;
			tp=tp + kromozomY[i];
		}
		int t=0;
		double x;
		int s = 0;
		int u=0;
		for(int j=0;j<kromozomSayisi;j++)
		{
			x = (kromozomY[j]/tp)*100;
			if((Math.ceil(x)-(x))<=0.5)
			{
				x = Math.ceil(x);
			}
			else
			{
				x = Math.floor(x);
			}
			int y = (int)x;
			y=y+s;
			if(y<=100)
			{
				for(u=s;u<y;u++)
				{
					yuzde[u] = t;
				}
			}
			t++;
			s=u;
		}
	
	}
	// bu fonksiyon yeni nesli �retmek amac�yla yaz�lm��t�r.
	private void yeniNesilUret(int esya,int cantaKapasitesi)	
	{
		uygunlukFonksiyonu(kromozomlar, esya, cantaKapasitesi);// her bir kromozomun se�ilme y�zdesi hesaplan�r.
		yeniNesilKromozomlar2.clear();
		while(yeniNesilKromozomlar2.size()<=kromozomSayisi-2)
		{
			Random rd = new Random();
			// rastgele iki say� �retilir ve y�zde[] dizisinden hangi
			// elemanlar�n se�ilece�ini g�sterir.
			int inds1 = yuzde[Math.abs(rd.nextInt()%100)];
			int inds2 = yuzde[Math.abs(rd.nextInt()%100)];
			crossover(inds1, inds2, esya, cantaKapasitesi);
		}
		degerHesapla(yeniNesilKromozomlar2, esya);
		degerMax();
		// �retilen yeni nesli eski nesile kopyalamak i�in arrCopy fonksiyonu �a�r�l�r.
		kromozomlar.clear();
		kromozomlar = arrCopy(yeniNesilKromozomlar2, kromozomlar, esya);
	}
	// bu fonksiyon Jenerasyon say�s� kadar yeni nesil �retir. her nesilde maksimum kromozom degerini hesaplay�p
	// bir �nceki maksimum degrden b�y�k ise de�i�tirir. en sonda da maksimum de�er kullan�c�ya g�sterilir. 
	private void jenerasyon(int esya,int cantaKapasitesi)
	{
		for(int i=0;i<jenerasyonSayisi;i++)
		{
			//System.out.println(i + ". jenerasyon");
			enBuyukKromozomuBul(esya);
			yeniNesilUret(esya, cantaKapasitesi);
			//degerMax();
			//System.out.println("MAXDeger = " + MAXVALUE);
		}
		System.out.println("MAXDeger = " + MAXVALUE);
		/*if(MAXVALUE==90)
		{
			ort++; 
		}*/
		bestOne2 = new Kromozom();
		bestOne2.getKromozom(esya);
		if(MAXVALUE2<MAXVALUE)
		{
			MAXVALUE2 = MAXVALUE;
			cozumyolu = " ";
			for(int a=0;a<esya;a++)
			{
				cozumyolu = cozumyolu + bestOne.kromozom[a];
				//System.out.print(bestOne.kromozom[a]);
			}
		}
		/*System.out.println("en iyi kromozom");
		for(int a=0;a<esya;a++)
		{
			System.out.print(bestOne.kromozom[a]);
		}*/
		System.out.println();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println();
	}
	// girdi olarak gelen arraylist' lerden birini digerine kopyalar.
	private ArrayList<Kromozom> arrCopy(ArrayList<Kromozom> arr1,ArrayList<Kromozom> arr2,int esya)
	{
		for(int i=0;i<arr1.size();i++)
		{
			Kromozom kro = new Kromozom();
			kro.getKromozom(esya);
			arr2.add(kro);
			for(int j=0;j<esya;j++)
			{
				arr2.get(i).kromozom[j] = arr1.get(i).kromozom[j];
			}
		}
		return arr2;
	}
	
	
	// en iyi kromozom bulundu�unda bu kromozomu tutmak i�in ilk ba�ta yer ay�rmada kullan�l�yor.
	private void enIyiyeYerAyir(int esya)
	{
		bestOne = new Kromozom();
		bestOne.getKromozom(esya);
	}
	private void degerMax()
	{
		double tmp = 0.0;
		for(int i=0;i<kromozomSayisi;i++)
		{
			if(tmp<degerler.get(i))
			{
				tmp = degerler.get(i);
			}
		}
		//System.out.println("maksimum deger = " + tmp);
	}
	private void enBuyukKromozomuBul(int esya)
	{
		degerHesapla(kromozomlar,esya);
		for(int i=0;i<kromozomSayisi;i++)
		{
			double deg = degerler.get(i);
			if(deg>MAXVALUE)
			{
				MAXVALUE = deg;
				for(int j=0;j<esya;j++)
				{
					bestOne.kromozom[j] = kromozomlar.get(i).kromozom[j];
				}
			}
		}
	}
	private void dnmEsyaOlustur2()
	{
		Items itms = new Items();
		itms.setAg�rl�k(5);
		itms.setDeger(10);
		esyalar.add(itms);
		Items itms2 = new Items();
		itms2.setAg�rl�k(4);
		itms2.setDeger(40);
		esyalar.add(itms2);
		Items itms3 = new Items();
		itms3.setAg�rl�k(6);
		itms3.setDeger(30);
		esyalar.add(itms3);
		Items itms4 = new Items();
		itms4.setAg�rl�k(3);
		itms4.setDeger(50);
		esyalar.add(itms4);
		
	}
	private void dnmEsyaOlustur()
	{
		Items itms = new Items();
		itms.setAg�rl�k(2);
		itms.setDeger(12);
		esyalar.add(itms);
		Items itms2 = new Items();
		itms2.setAg�rl�k(1);
		itms2.setDeger(10);
		esyalar.add(itms2);
		Items itms3 = new Items();
		itms3.setAg�rl�k(3);
		itms3.setDeger(20);
		esyalar.add(itms3);
		Items itms4 = new Items();
		itms4.setAg�rl�k(2);
		itms4.setDeger(15);
		esyalar.add(itms4);
		Items itms5 = new Items();
		itms5.setAg�rl�k(2);
		itms5.setDeger(12);
		esyalar.add(itms5);
		Items itms6 = new Items();
		itms6.setAg�rl�k(1);
		itms6.setDeger(1);
		esyalar.add(itms6);
		Items itms7 = new Items();
		itms7.setAg�rl�k(3);
		itms7.setDeger(2);
		esyalar.add(itms7);
		Items itms8 = new Items();
		itms8.setAg�rl�k(2);
		itms8.setDeger(8);
		esyalar.add(itms8);
		Items itms9 = new Items();
		itms9.setAg�rl�k(5);
		itms9.setDeger(9);
		esyalar.add(itms9);
		Items itms10 = new Items();
		itms10.setAg�rl�k(5);
		itms10.setDeger(6);
		esyalar.add(itms10);
		Items itms11 = new Items();
		itms11.setAg�rl�k(9);
		itms11.setDeger(20);
		esyalar.add(itms11);
		Items itms12 = new Items();
		itms12.setAg�rl�k(1);
		itms12.setDeger(18);
		esyalar.add(itms12);
		Items itms13 = new Items();
		itms13.setAg�rl�k(2);
		itms13.setDeger(1);
		esyalar.add(itms13);
		Items itms14 = new Items();
		itms14.setAg�rl�k(3);
		itms14.setDeger(7);
		esyalar.add(itms14);
		Items itms15 = new Items();
		itms15.setAg�rl�k(5);
		itms15.setDeger(20);
		esyalar.add(itms15);
		Items itms16 = new Items();
		itms16.setAg�rl�k(4);
		itms16.setDeger(60);
		esyalar.add(itms16);
	}
	private void kromozomYazd�r(ArrayList<Kromozom> array,int esya)
	{
		for(int a=0;a<array.size();a++)
		{
			for(int b=0;b<esya;b++)
			{
				System.out.print("" + array.get(a).kromozom[b]);
			}
			System.out.println();
		}
	}
}
