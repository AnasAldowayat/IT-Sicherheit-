/**
 * 
 */
package secretsharing;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

/**
 * This class implements the simple XOR-based (n,n) secret sharing.
 * 
 * Secrets and shares are both represented as byte[] arrays.
 * 
 * Randomness is taken from a {@link java.security.SecureRandom} object.
 * 
 * @see SecureRandom
 * 
 */
public class XorSecretSharing {

	/**
	 * Creates a XOR secret sharing object for n shares
	 * 
	 * @param n
	 *            number of shares to use. Needs to fulfill n >= 2.
	 */
	public XorSecretSharing(int n) {
		assert (n >= 2);
		this.n = n;
		this.rng = new SecureRandom();
	}

	/**
	 * Shares the secret into n parts.
	 * 
	 * @param secret
	 *            The secret to share.
	 * 
	 * @return An array of the n shares.
	 */
	public byte[][] share(final byte[] secret) {
		// TODO: implement this
		byte[][] shares = new byte[n][secret.length];
		for (int i = 0; i < secret.length; i++) {
			shares[0][i] = secret[i];
		}
		for (int i = 1; i < n; i++) {
			rng.nextBytes(shares[i]);
			for (int j = 0; j < secret.length; j++) {
				shares[0][j] ^= shares[i][j];
			}
		}
		return shares;
	}

	/**
	 * Recombines the given shares into the secret.
	 * 
	 * @param shares
	 *            The complete set of n shares for this secret.
	 * 
	 * @return The reconstructed secret.
	 */
	public byte[] combine(final byte[][] shares) {

		// TODO: implement this
		byte[] secret = new byte[shares[0].length];
		for (int i = 0; i < shares[0].length; i++) {
			secret[i] = shares[0][i];
		}
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < shares[0].length; j++) {
				secret[j] ^= shares[i][j];
			}
		}
		return secret;
	}

	private int n;

	public int getN() {
		return n;
	}

	private Random rng;

	public static void main(String[] args) {

		XorSecretSharing sss = new XorSecretSharing(5);
		byte[] secret = new byte[10];
		sss.rng.nextBytes(secret);
		System.out.println("Secret: " + Arrays.toString(secret));
		byte[][] shares = sss.share(secret);
		System.out.println("Shares: " + Arrays.deepToString(shares));
		byte[] secret2 = sss.combine(shares);
		System.out.println("Combined: " + Arrays.toString(secret2));
		System.out.println(Arrays.equals(secret, secret2));

	}
}
