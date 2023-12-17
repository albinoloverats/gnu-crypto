package gnu.testlet.gnu.crypto.cipher;

// ----------------------------------------------------------------------------
// $Id: TestOfDES.java,v 1.5 2005/10/06 04:24:19 rsdio Exp $
//
// Copyright (C) 2001, 2002, 2003 Free Software Foundation, Inc.
//
// This file is part of GNU Crypto.
//
// GNU Crypto is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2, or (at your option)
// any later version.
//
// GNU Crypto is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; see the file COPYING.  If not, write to the
//
//    Free Software Foundation Inc.,
//    51 Franklin Street, Fifth Floor,
//    Boston, MA 02110-1301
//    USA
//
// Linking this library statically or dynamically with other modules is
// making a combined work based on this library.  Thus, the terms and
// conditions of the GNU General Public License cover the whole
// combination.
//
// As a special exception, the copyright holders of this library give
// you permission to link this library with independent modules to
// produce an executable, regardless of the license terms of these
// independent modules, and to copy and distribute the resulting
// executable under terms of your choice, provided that you also meet,
// for each linked independent module, the terms and conditions of the
// license of that module.  An independent module is a module which is
// not derived from or based on this library.  If you modify this
// library, you may extend this exception to your version of the
// library, but you are not obligated to do so.  If you do not wish to
// do so, delete this exception statement from your version.
// ----------------------------------------------------------------------------

// Tags: GNU-CRYPTO
// Uses: BaseCipherTestCase

import gnu.crypto.Properties;
import gnu.crypto.cipher.DES;
import gnu.crypto.cipher.IBlockCipher;
import gnu.crypto.cipher.WeakKeyException;
import gnu.crypto.util.Util;
import gnu.testlet.TestHarness;
import java.util.Arrays;
import java.util.HashMap;

/**
 * <p>Conformance test for the DES cipher.</p>
 *
 * @version $Revision: 1.5 $
 */
public class TestOfDES extends BaseCipherTestCase {

   // Constants and variables.
   // -----------------------------------------------------------------------

   /**
    * <p>Test vectors from NBS SP-500, "Validating the Correctness of Hardware
    * Implementations of the NBS Data Encryption Standard".</p>
    */
   static final String[][] TV = {
      // key bytes           clear bytes         cipher bytes
      // IP and E test
      { "0101010101010101", "95F8A5E5DD31D900", "8000000000000000" },
      { "0101010101010101", "DD7F121CA5015619", "4000000000000000" },
      { "0101010101010101", "2E8653104F3834EA", "2000000000000000" },
      { "0101010101010101", "4BD388FF6CD81D4F", "1000000000000000" },
      { "0101010101010101", "20B9E767B2FB1456", "0800000000000000" },
      { "0101010101010101", "55579380D77138EF", "0400000000000000" },
      { "0101010101010101", "6CC5DEFAAF04512F", "0200000000000000" },
      { "0101010101010101", "0D9F279BA5D87260", "0100000000000000" },
      { "0101010101010101", "D9031B0271BD5A0A", "0080000000000000" },
      { "0101010101010101", "424250B37C3DD951", "0040000000000000" },
      { "0101010101010101", "B8061B7ECD9A21E5", "0020000000000000" },
      { "0101010101010101", "F15D0F286B65BD28", "0010000000000000" },
      { "0101010101010101", "ADD0CC8D6E5DEBA1", "0008000000000000" },
      { "0101010101010101", "E6D5F82752AD63D1", "0004000000000000" },
      { "0101010101010101", "ECBFE3BD3F591A5E", "0002000000000000" },
      { "0101010101010101", "F356834379D165CD", "0001000000000000" },
      { "0101010101010101", "2B9F982F20037FA9", "0000800000000000" },
      { "0101010101010101", "889DE068A16F0BE6", "0000400000000000" },
      { "0101010101010101", "E19E275D846A1298", "0000200000000000" },
      { "0101010101010101", "329A8ED523D71AEC", "0000100000000000" },
      { "0101010101010101", "E7FCE22557D23C97", "0000080000000000" },
      { "0101010101010101", "12A9F5817FF2D65D", "0000040000000000" },
      { "0101010101010101", "A484C3AD38DC9C19", "0000020000000000" },
      { "0101010101010101", "FBE00A8A1EF8AD72", "0000010000000000" },
      { "0101010101010101", "750D079407521363", "0000008000000000" },
      { "0101010101010101", "64FEED9C724C2FAF", "0000004000000000" },
      { "0101010101010101", "F02B263B328E2B60", "0000002000000000" },
      { "0101010101010101", "9D64555A9A10B852", "0000001000000000" },
      { "0101010101010101", "D106FF0BED5255D7", "0000000800000000" },
      { "0101010101010101", "E1652C6B138C64A5", "0000000400000000" },
      { "0101010101010101", "E428581186EC8F46", "0000000200000000" },
      { "0101010101010101", "AEB5F5EDE22D1A36", "0000000100000000" },
      { "0101010101010101", "E943D7568AEC0C5C", "0000000080000000" },
      { "0101010101010101", "DF98C8276F54B04B", "0000000040000000" },
      { "0101010101010101", "B160E4680F6C696F", "0000000020000000" },
      { "0101010101010101", "FA0752B07D9C4AB8", "0000000010000000" },
      { "0101010101010101", "CA3A2B036DBC8502", "0000000008000000" },
      { "0101010101010101", "5E0905517BB59BCF", "0000000004000000" },
      { "0101010101010101", "814EEB3B91D90726", "0000000002000000" },
      { "0101010101010101", "4D49DB1532919C9F", "0000000001000000" },
      { "0101010101010101", "25EB5FC3F8CF0621", "0000000000800000" },
      { "0101010101010101", "AB6A20C0620D1C6F", "0000000000400000" },
      { "0101010101010101", "79E90DBC98F92CCA", "0000000000200000" },
      { "0101010101010101", "866ECEDD8072BB0E", "0000000000100000" },
      { "0101010101010101", "8B54536F2F3E64A8", "0000000000080000" },
      { "0101010101010101", "EA51D3975595B86B", "0000000000040000" },
      { "0101010101010101", "CAFFC6AC4542DE31", "0000000000020000" },
      { "0101010101010101", "8DD45A2DDF90796C", "0000000000010000" },
      { "0101010101010101", "1029D55E880EC2D0", "0000000000008000" },
      { "0101010101010101", "5D86CB23639DBEA9", "0000000000004000" },
      { "0101010101010101", "1D1CA853AE7C0C5F", "0000000000002000" },
      { "0101010101010101", "CE332329248F3228", "0000000000001000" },
      { "0101010101010101", "8405D1ABE24FB942", "0000000000000800" },
      { "0101010101010101", "E643D78090CA4207", "0000000000000400" },
      { "0101010101010101", "48221B9937748A23", "0000000000000200" },
      { "0101010101010101", "DD7C0BBD61FAFD54", "0000000000000100" },
      { "0101010101010101", "2FBC291A570DB5C4", "0000000000000080" },
      { "0101010101010101", "E07C30D7E4E26E12", "0000000000000040" },
      { "0101010101010101", "0953E2258E8E90A1", "0000000000000020" },
      { "0101010101010101", "5B711BC4CEEBF2EE", "0000000000000010" },
      { "0101010101010101", "CC083F1E6D9E85F6", "0000000000000008" },
      { "0101010101010101", "D2FD8867D50D2DFE", "0000000000000004" },
      { "0101010101010101", "06E7EA22CE92708F", "0000000000000002" },
      { "0101010101010101", "166B40B44ABA4BD6", "0000000000000001" },

      // PC1 and PC2 test
      { "8001010101010101", "0000000000000000", "95A8D72813DAA94D" },
      { "4001010101010101", "0000000000000000", "0EEC1487DD8C26D5" },
      { "2001010101010101", "0000000000000000", "7AD16FFB79C45926" },
      { "1001010101010101", "0000000000000000", "D3746294CA6A6CF3" },
      { "0801010101010101", "0000000000000000", "809F5F873C1FD761" },
      { "0401010101010101", "0000000000000000", "C02FAFFEC989D1FC" },
      { "0201010101010101", "0000000000000000", "4615AA1D33E72F10" },
      { "0180010101010101", "0000000000000000", "2055123350C00858" },
      { "0140010101010101", "0000000000000000", "DF3B99D6577397C8" },
      { "0120010101010101", "0000000000000000", "31FE17369B5288C9" },
      { "0110010101010101", "0000000000000000", "DFDD3CC64DAE1642" },
      { "0108010101010101", "0000000000000000", "178C83CE2B399D94" },
      { "0104010101010101", "0000000000000000", "50F636324A9B7F80" },
      { "0102010101010101", "0000000000000000", "A8468EE3BC18F06D" },
      { "0101800101010101", "0000000000000000", "A2DC9E92FD3CDE92" },
      { "0101400101010101", "0000000000000000", "CAC09F797D031287" },
      { "0101200101010101", "0000000000000000", "90BA680B22AEB525" },
      { "0101100101010101", "0000000000000000", "CE7A24F350E280B6" },
      { "0101080101010101", "0000000000000000", "882BFF0AA01A0B87" },
      { "0101040101010101", "0000000000000000", "25610288924511C2" },
      { "0101020101010101", "0000000000000000", "C71516C29C75D170" },
      { "0101018001010101", "0000000000000000", "5199C29A52C9F059" },
      { "0101014001010101", "0000000000000000", "C22F0A294A71F29F" },
      { "0101012001010101", "0000000000000000", "EE371483714C02EA" },
      { "0101011001010101", "0000000000000000", "A81FBD448F9E522F" },
      { "0101010801010101", "0000000000000000", "4F644C92E192DFED" },
      { "0101010401010101", "0000000000000000", "1AFA9A66A6DF92AE" },
      { "0101010201010101", "0000000000000000", "B3C1CC715CB879D8" },
      { "0101010180010101", "0000000000000000", "19D032E64AB0BD8B" },
      { "0101010140010101", "0000000000000000", "3CFAA7A7DC8720DC" },
      { "0101010120010101", "0000000000000000", "B7265F7F447AC6F3" },
      { "0101010110010101", "0000000000000000", "9DB73B3C0D163F54" },
      { "0101010108010101", "0000000000000000", "8181B65BABF4A975" },
      { "0101010104010101", "0000000000000000", "93C9B64042EAA240" },
      { "0101010102010101", "0000000000000000", "5570530829705592" },
      { "0101010101800101", "0000000000000000", "8638809E878787A0" },
      { "0101010101400101", "0000000000000000", "41B9A79AF79AC208" },
      { "0101010101200101", "0000000000000000", "7A9BE42F2009A892" },
      { "0101010101100101", "0000000000000000", "29038D56BA6D2745" },
      { "0101010101080101", "0000000000000000", "5495C6ABF1E5DF51" },
      { "0101010101040101", "0000000000000000", "AE13DBD561488933" },
      { "0101010101020101", "0000000000000000", "024D1FFA8904E389" },
      { "0101010101018001", "0000000000000000", "D1399712F99BF02E" },
      { "0101010101014001", "0000000000000000", "14C1D7C1CFFEC79E" },
      { "0101010101012001", "0000000000000000", "1DE5279DAE3BED6F" },
      { "0101010101011001", "0000000000000000", "E941A33F85501303" },
      { "0101010101010801", "0000000000000000", "DA99DBBC9A03F379" },
      { "0101010101010401", "0000000000000000", "B7FC92F91D8E92E9" },
      { "0101010101010201", "0000000000000000", "AE8E5CAA3CA04E85" },
      { "0101010101010180", "0000000000000000", "9CC62DF43B6EED74" },
      { "0101010101010140", "0000000000000000", "D863DBB5C59A91A0" },
      { "0101010101010120", "0000000000000000", "A1AB2190545B91D7" },
      { "0101010101010110", "0000000000000000", "0875041E64C570F7" },
      { "0101010101010108", "0000000000000000", "5A594528BEBEF1CC" },
      { "0101010101010104", "0000000000000000", "FCDB3291DE21F0C0" },
      { "0101010101010102", "0000000000000000", "869EFD7F9F265A09" },

      // P test
      { "1046913489980131", "0000000000000000", "88D55E54F54C97B4" },
      { "1007103489988020", "0000000000000000", "0C0CC00C83EA48FD" },
      { "10071034C8980120", "0000000000000000", "83BC8EF3A6570183" },
      { "1046103489988020", "0000000000000000", "DF725DCAD94EA2E9" },
      { "1086911519190101", "0000000000000000", "E652B53B550BE8B0" },
      { "1086911519580101", "0000000000000000", "AF527120C485CBB0" },
      { "5107B01519580101", "0000000000000000", "0F04CE393DB926D5" },
      { "1007B01519190101", "0000000000000000", "C9F00FFC74079067" },
      { "3107915498080101", "0000000000000000", "7CFD82A593252B4E" },
      { "3107919498080101", "0000000000000000", "CB49A2F9E91363E3" },
      { "10079115B9080140", "0000000000000000", "00B588BE70D23F56" },
      { "3107911598090140", "0000000000000000", "406A9A6AB43399AE" },
      { "1007D01589980101", "0000000000000000", "6CB773611DCA9ADA" },
      { "9107911589980101", "0000000000000000", "67FD21C17DBB5D70" },
      { "9107D01589190101", "0000000000000000", "9592CB4110430787" },
      { "1007D01598980120", "0000000000000000", "A6B7FF68A318DDD3" },
      { "1007940498190101", "0000000000000000", "4D102196C914CA16" },
      { "0107910491190401", "0000000000000000", "2DFA9F4573594965" },
      { "0107910491190101", "0000000000000000", "B46604816C0E0774" },
      { "0107940491190401", "0000000000000000", "6E7E6221A4F34E87" },
      { "19079210981A0101", "0000000000000000", "AA85E74643233199" },
      { "1007911998190801", "0000000000000000", "2E5A19DB4D1962D6" },
      { "10079119981A0801", "0000000000000000", "23A866A809D30894" },
      { "1007921098190101", "0000000000000000", "D812D961F017D320" },
      { "100791159819010B", "0000000000000000", "055605816E58608F" },
      { "1004801598190101", "0000000000000000", "ABD88E8B1B7716F1" },
      { "1004801598190102", "0000000000000000", "537AC95BE69DA1E1" },
      { "1004801598190108", "0000000000000000", "AED0F6AE3C25CDD8" },
      { "1002911598100104", "0000000000000000", "B3E35A5EE53E7B8D" },
      { "1002911598190104", "0000000000000000", "61C79C71921A2EF8" },
      { "1002911598100201", "0000000000000000", "E2F5728F0995013C" },
      { "1002911698100101", "0000000000000000", "1AEAC39A61F0A464" },

      // S-Box test.
      { "7CA110454A1A6E57", "01A1D6D039776742", "690F5B0D9A26939B" },
      { "0131D9619DC1376E", "5CD54CA83DEF57DA", "7A389D10354BD271" },
      { "07A1133E4A0B2686", "0248D43806F67172", "868EBB51CAB4599A" },
      { "3849674C2602319E", "51454B582DDF440A", "7178876E01F19B2A" },
      { "04B915BA43FEB5B6", "42FD443059577FA2", "AF37FB421F8C4095" },
      { "0113B970FD34F2CE", "059B5E0851CF143A", "86A560F10EC6D85B" },
      { "0170F175468FB5E6", "0756D8E0774761D2", "0CD3DA020021DC09" },
      { "43297FAD38E373FE", "762514B829BF486A", "EA676B2CB7DB2B7A" },
      { "07A7137045DA2A16", "3BDD119049372802", "DFD64A815CAF1A0F" },
      { "04689104C2FD3B2F", "26955F6835AF609A", "5C513C9C4886C088" },
      { "37D06BB516CB7546", "164D5E404F275232", "0A2AEEAE3FF4AB77" },
      { "1F08260D1AC2465E", "6B056E18759F5CCA", "EF1BF03E5DFA575A" },
      { "584023641ABA6176", "004BD6EF09176062", "88BF0DB6D70DEE56" },
      { "025816164629B007", "480D39006EE762F2", "A1F9915541020B56" },
      { "49793EBC79B3258F", "437540C8698F3CFA", "6FBF1CAFCFFD0556" },
      { "4FB05E1515AB73A7", "072D43A077075292", "2F22E49BAB7CA1AC" },
      { "49E95D6D4CA229BF", "02FE55778117F12A", "5A6B612CC26CCE4A" },
      { "018310DC409B26D6", "1D9D5C5018F728C2", "5F4C038ED12B2E41" },
      { "1C587F1C13924FEF", "305532286D6F295A", "63FAC0D034D9F793" }
   };

   // Constructors.
   // -----------------------------------------------------------------------

   // default 0-arguments constructor

   // Class methods.
   // -----------------------------------------------------------------------

   // Instance methods.
   // -----------------------------------------------------------------------

   public void test(TestHarness harness) {
      harness.checkPoint("TestOfDES");
      cipher = new DES();
      HashMap attrib = new HashMap();
      attrib.put(IBlockCipher.CIPHER_BLOCK_SIZE, new Integer(8));
      attrib.put(IBlockCipher.KEY_MATERIAL, new byte[8]);
      boolean oldCheckForWeakKeys = Properties.checkForWeakKeys();
      try {
         Properties.setCheckForWeakKeys(false);

         harness.check(validityTest(), "validityTest()");
         harness.check(cloneabilityTest(), "cloneabilityTest()");
         harness.check(vectorsTest(), "vectorsTest()");

         Properties.setCheckForWeakKeys(true);

         test4WeakKeys(harness);

      } catch (Exception x) {
         harness.debug(x);
         harness.fail("TestOfDES");
      } finally { // return it to its previous value
         Properties.setCheckForWeakKeys(oldCheckForWeakKeys);
      }
   }

   /** Test cloneability. */
   protected boolean cloneabilityTest() throws Exception {
      int blockSize = cipher.defaultBlockSize();
      int keySize = cipher.defaultKeySize();

      byte[] pt = new byte[blockSize];
      byte[] ct1 = new byte[blockSize];
      byte[] ct2 = new byte[blockSize];
      byte[] kb = new byte[keySize];
      HashMap attributes = new HashMap();
      attributes.put(IBlockCipher.KEY_MATERIAL, kb);

      cipher.reset();
      cipher.init(attributes);

      cipher.encryptBlock(pt, 0, pt, 0);
      IBlockCipher thomas = (IBlockCipher) cipher.clone();
      thomas.init(attributes);
      cipher.encryptBlock(pt, 0, ct1, 0);
      thomas.encryptBlock(pt, 0, ct2, 0);

      return Arrays.equals(ct1, ct2);
   }

   protected boolean vectorsTest() throws Exception {
      HashMap attrib = new HashMap();
      byte[] kb, pt, ct1, ct2 = new byte[8], cpt = new byte[8];
      for (int i = 0; i < TV.length; i++) {
         kb = Util.toBytesFromString(TV[i][0]);
         pt = Util.toBytesFromString(TV[i][1]);
         ct1 = Util.toBytesFromString(TV[i][2]);
         attrib.put(IBlockCipher.KEY_MATERIAL, kb);
         cipher.reset();
         cipher.init(attrib);
         cipher.encryptBlock(pt, 0, ct2, 0);
         if (!Arrays.equals(ct1, ct2)) {
            return false;
         }
         cipher.decryptBlock(ct2, 0, cpt, 0);
         if (!Arrays.equals(pt, cpt)) {
            return false;
         }
      }
      return true;
   }

   private void test4WeakKeys(TestHarness harness) {
      harness.checkPoint("TestOfDES.test4WeakKeys");

      DES des = (DES) cipher;
      String msg;
      int i;
      for (i = 0; i < DES.WEAK_KEYS.length; i++) {
         msg = "detecting weak key 0x"+Util.dumpString(DES.WEAK_KEYS[i]);
         try {
            des.makeKey(DES.WEAK_KEYS[i], DES.KEY_SIZE);
            harness.fail(msg);
         } catch (WeakKeyException x) {
            harness.check(true, msg);
         } catch (Exception x) {
            harness.debug(x);
            harness.fail(msg+": "+String.valueOf(x));
         }
      }

      for (i = 0; i < DES.SEMIWEAK_KEYS.length; i++) {
         msg = "detecting semi-weak key 0x"+Util.dumpString(DES.SEMIWEAK_KEYS[i]);
         try {
            des.makeKey(DES.SEMIWEAK_KEYS[i], DES.KEY_SIZE);
            harness.fail(msg);
         } catch (WeakKeyException x) {
            harness.check(true, msg);
         } catch (Exception x) {
            harness.debug(x);
            harness.fail(msg+": "+String.valueOf(x));
         }
      }

      for (i = 0; i < DES.POSSIBLE_WEAK_KEYS.length; i++) {
         msg = "detecting possible weak key 0x"+Util.dumpString(DES.POSSIBLE_WEAK_KEYS[i]);
         try {
            des.makeKey(DES.POSSIBLE_WEAK_KEYS[i], DES.KEY_SIZE);
            harness.fail(msg);
         } catch (WeakKeyException x) {
            harness.check(true, msg);
         } catch (Exception x) {
            harness.debug(x);
            harness.fail(msg+": "+String.valueOf(x));
         }
      }
   }
}
