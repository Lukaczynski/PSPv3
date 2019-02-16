import javax.crypto.Cipher;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

public class Main {
    /**
     * Servidor para ejemplo de dos aplicaciones que se comuniquen exclusivamente con RSA.
     * Para que esto sea posible el dadas las limitaciones del tamaño del mensaje que puede cifrar el algoritmo las claves
     * del servidor se generan con 2048 bytes los que nos da unos 245 bytes para cifrar el mensjae.
     * Esto supone que no podremos cifrar nuestra clave publica por parte del cliente si tiene el mismo tamaño por lo que
     * reducimos el tamabo de las claves en el cliente a 1024 lo que nos da 117 bytes para cifrar los datos
     * (Si el mensaje es mas grande habria que partirlo en trozos menores que este tamaño o  hacer lo que normalmente se
     * hace y utilizar cifrado simetrico para la comunicacion posterior)
     *
     * @param args
     * @throws Exception
     */

    private static ServerSocket server;
    private static int port = 8080;

    public static void main(String args[]) throws Exception {


        final int keySize = 2048;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        KeyPair nuestrasclaves = keyPairGenerator.genKeyPair();

        server = new ServerSocket(port);



        Socket socket = server.accept();
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        oos.writeObject(nuestrasclaves.getPublic()); // Enviamos nuestra clave publica xD



        PublicKey publicKeyClient = KeyFactory.getInstance("RSA")
                .generatePublic(
                        new X509EncodedKeySpec(
                                decrypt(nuestrasclaves.getPrivate(), (byte[]) ois.readObject()
                                )
                        )
                );//Recibimos la clave publica del cliente que esta cifrada con nuestra clave publica


        oos.writeObject(encrypt(publicKeyClient, "Hola aplicacion 1".getBytes()));
        System.out.print(new String(decrypt(publicKeyClient, (byte[]) ois.readObject())));
        ois.close();
        oos.close();
        socket.close();
        server.close();

    }

    /**
     * @param key     Key Clave para cifrar los datos
     * @param message byte[] El tamaño depende del tamaño de clave
     * @return byte[] el mensaje cifrado con la clave proporcionada
     * @throws Exception
     */
    public static byte[] encrypt(Key key, byte[] message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(message);
    }
    /**
     * @param key       Key Clave para descifrar los datos
     * @param encrypted byte[] el mensaje cifrado
     * @return byte[] el mensae en claro si seha utilizado la clave correcta
     * @throws Exception
     */
    public static byte[] decrypt(Key key, byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(encrypted);
    }
}
