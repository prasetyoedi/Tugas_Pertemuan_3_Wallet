import org.example.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {
    private Wallet wallet;

    @BeforeEach
    void init() {
        wallet = new Wallet("Edi");
    }

    @Test
    void testSetOwnerNotNull() {
        wallet.setOwner("Prasetyo");
        assertNotNull(wallet.owner);
        assertEquals("Prasetyo", wallet.owner);
    }

    @Test
    void testKartuNotNull() {
        Wallet wallet = new Wallet("Edi");
        assertNotNull(wallet.listKartu);
    }

    @Test
    void testUangKoinNotNull() {
        assertNull(wallet.listUangKoin);
    }

    @Test
    void testUangLembaranNotNull() {
        assertNotNull(wallet.listUangLembaran);
    }
    @Test
    void testTambahKartu() {
        wallet.tambahkartu("Kartu Mandiri");
        assertEquals(1, wallet.listKartu.size());
        assertTrue(wallet.listKartu.contains("Kartu Mandiri"));

        wallet.tambahkartu("Kartu BNI");
        assertEquals(2, wallet.listKartu.size());
        assertTrue(wallet.listKartu.contains("Kartu BNI"));
    }

    @Test
    void testAmbilKartu() {
        wallet.tambahkartu("Kartu Mandiri");
        wallet.tambahkartu("Kartu BNI");

        String kartu1 = wallet.ambilKartu("Kartu Mandiri");
        assertEquals("Kartu Mandiri", kartu1);
        assertEquals(1, wallet.listKartu.size());
        assertFalse(wallet.listKartu.contains("Kartu Mandiri"));

        String kartu2 = wallet.ambilKartu("Kartu BNI");
        assertEquals("Kartu BNI", kartu2);
        assertEquals(0, wallet.listKartu.size());
        assertFalse(wallet.listKartu.contains("Kartu BNI"));

        String kartu3 = wallet.ambilKartu("Kartu PayPall");
        assertNull(kartu3);
    }

    @Test
    void testTambahUangRupiah() {
        wallet.tambahUangRupiah(500);
        assertTrue(wallet.listUangKoin.contains(500));

        wallet.tambahUangRupiah(2000);
        assertTrue(wallet.listUangLembaran.contains(2000));
    }

    @Test
    void testAmbilUang() {
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
    void testTampilkanUang() {
        wallet.tambahUangRupiah(500);
        wallet.tambahUangRupiah(2000);

        assertEquals(2500, wallet.tampilkanUang());
    }

    @Test
    void testTambahKartuArrayEquals() {
        wallet.tambahkartu("Kartu Mandiri");
        wallet.tambahkartu("Kartu BNI");

        String[] expectedCards = {"Kartu Mandiri", "Kartu BNI"};
        assertArrayEquals(expectedCards, wallet.listKartu.toArray());
    }

    @Test
    void testTampilkanUangAll() {
        Wallet wallet = new Wallet("Edi");
        wallet.tambahUangRupiah(500);
        wallet.tambahUangRupiah(2000);

        assertAll("Jumlah uang",
                () -> assertTrue(wallet.tampilkanUang() > 0),
                () -> assertEquals(2500, wallet.tampilkanUang())
        );
    }
}