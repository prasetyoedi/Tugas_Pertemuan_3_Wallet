import org.example.Wallet;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WalletTestLifeCycle {
    private Wallet wallet;
    private int initialBalance;

    @BeforeAll
    void initAll() {
        System.out.println("Sebelum memulai pengujian, inisiasi wallet dengan nama pemilik Edi");
        wallet = new Wallet("Edi");
    }

    @BeforeEach
    void init() {
        System.out.println("Sebelum setiap pengujian, inisiasi wallet dengan nama pemilik Edi, tambahkan kartu BNI dan MANDIRI, serta tambahkan saldo awal sebesar 10000");
        wallet = new Wallet("Edi");
        wallet.tambahkartu("Kartu BNI");
        wallet.tambahkartu("Kartu MANDIRI");
        wallet.tambahUangRupiah(10000);
        initialBalance = wallet.tampilkanUang();
    }

    @Test
    void testSetOwnerNotNull() {
        System.out.println("Pengujian apakah pengaturan pemilik wallet berhasil atau tidak");
        wallet.setOwner("Prasetyo");
        assertNotNull(wallet.owner);
        assertEquals("Prasetyo", wallet.owner);
    }

    @Test
    void testKartuNotNull() {
        System.out.println("Pengujian apakah kartu tidak kosong");
        assertNotNull(wallet.listKartu);
    }

    @Test
    void testUangKoinNotNull() {
        System.out.println("Pengujian apakah uang koin tidak kosong dan uang 100 berhasil ditambahkan");
        wallet.tambahUangRupiah(100);
        assertNotNull(wallet.listUangKoin);
        assertTrue(wallet.listUangKoin.contains(100));
    }

    @Test
    void testUangLembaranNotNull() {
        System.out.println("Pengujian apakah uang lembaran tidak kosong dan uang 2000 berhasil ditambahkan");
        wallet.tambahUangRupiah(2000);
        assertNotNull(wallet.listUangLembaran);
        assertTrue(wallet.listUangLembaran.contains(2000));
    }

    @Test
    void testTambahKartu() {
        System.out.println("Pengujian penambahan kartu BCA dan BRI");
        wallet.tambahkartu("Kartu BCA");
        assertEquals(3, wallet.listKartu.size());
        assertTrue(wallet.listKartu.contains("Kartu BCA"));

        wallet.tambahkartu("Kartu BRI");
        assertEquals(4, wallet.listKartu.size());
        assertTrue(wallet.listKartu.contains("Kartu BRI"));
    }

    @Test
    void testAmbilKartu() {
        System.out.println("Pengujian pengambilan kartu MANDIRI");
        String kartu1 = wallet.ambilKartu("Kartu MANDIRI");
        assertEquals("Kartu MANDIRI", kartu1);
        assertEquals(1, wallet.listKartu.size());
        assertFalse(wallet.listKartu.contains("Kartu MANDIRI"));
    }

    @Test
    void testTambahUangRupiah() {
        System.out.println("Pengujian penambahan uang 500 dan 2000");
        wallet.tambahUangRupiah(500);
        assertTrue(wallet.listUangKoin.contains(500));

        wallet.tambahUangRupiah(2000);
        assertTrue(wallet.listUangLembaran.contains(2000));
    }

    @Test
    void testAmbilUang() {
        System.out.println("Pengujian pengambilan uang 2000 dan 500");
        wallet.tambahUangRupiah(500);
        wallet.tambahUangRupiah(2000);

        int uangAmbil = wallet.ambilUang(2000);
        assertEquals(2000, uangAmbil);
        assertFalse(wallet.listUangLembaran.contains(2000));

        uangAmbil = wallet.ambilUang(500);
        assertEquals(500, uangAmbil);
        assertFalse(wallet.listUangKoin.contains(500));

        uangAmbil = wallet.ambilUang(100);
        assertEquals(0, uangAmbil);
    }

    @Test
    void testTampilkanUangSaatIni() {
        System.out.println("Pengujian menampilkan saldo saat ini");
        assertEquals(10000, wallet.tampilkanUang());
    }

    @Test
    void testTambahKartuArrayEquals() {
        System.out.println("Pengujian penambahan kartu yang memastikan array kartu sesuai dengan yang diharapkan");
        String[] expectedCards = {"Kartu BNI", "Kartu MANDIRI"};
        assertArrayEquals(expectedCards, wallet.listKartu.toArray());
    }

    @Test
    void testTampilkanUangAll() {
        System.out.println("Pengujian menampilkan jumlah uang secara keseluruhan");
        wallet.tambahUangRupiah(500);
        wallet.tambahUangRupiah(2000);

        assertAll("Jumlah uang",
                () -> assertTrue(wallet.tampilkanUang() > 0),
                () -> assertEquals(12500, wallet.tampilkanUang())
        );
    }

    @AfterEach
    void tearDown() {
        System.out.println("Setelah setiap pengujian, membersihkan daftar uang koin dan uang lembaran");
        wallet.listUangKoin.clear();
        wallet.listUangLembaran.clear();
    }

    @AfterAll
    void tearDownAll() {
        System.out.println("Setelah semua pengujian selesai, kembalikan saldo wallet ke nominal awal");
        wallet.tambahUangRupiah(initialBalance);
    }
}
