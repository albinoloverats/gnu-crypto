package gnu.crypto.sasl.crammd5;

// ----------------------------------------------------------------------------
// $Id: CramMD5Client.java,v 1.3 2005/10/06 04:24:18 rsdio Exp $
//
// Copyright (C) 2003 Free Software Foundation, Inc.
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

import gnu.crypto.Registry;
import gnu.crypto.sasl.ClientMechanism;
import gnu.crypto.util.Util;

import java.io.IOException;
import java.security.InvalidKeyException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.AuthenticationException;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslException;

/**
 * The CRAM-MD5 SASL client-side mechanism.
 *
 * @version $Revision: 1.3 $
 */
public class CramMD5Client extends ClientMechanism implements SaslClient {

   // Constants and variables
   // -------------------------------------------------------------------------

   // Constructor(s)
   // -------------------------------------------------------------------------

   public CramMD5Client() {
      super(Registry.SASL_CRAM_MD5_MECHANISM);
   }

   // Class methods
   // -------------------------------------------------------------------------

   // abstract methods implementation -----------------------------------------

   protected void initMechanism() throws SaslException {
   }

   protected void resetMechanism() throws SaslException {
   }

   // javax.security.sasl.SaslClient interface implementation -----------------

   public boolean hasInitialResponse() {
      return false;
   }

   public byte[] evaluateChallenge(final byte[] challenge) throws SaslException {
      if (challenge == null) {
         throw new SaslException("null challenge");
      }
      try {
         final String username;
         final char[] password;
         Callback[] callbacks;

         if ((!properties.containsKey(Registry.SASL_USERNAME))
               && (!properties.containsKey(Registry.SASL_PASSWORD))) {
            callbacks = new Callback[2];

            final NameCallback nameCB;
            final String defaultName = System.getProperty("user.name");
            if (defaultName == null) {
               nameCB = new NameCallback("username: ");
            } else {
               nameCB = new NameCallback("username: ",defaultName);
            }
            final PasswordCallback pwdCB = new PasswordCallback("password: ",false);
            callbacks[0] = nameCB;
            callbacks[1] = pwdCB;
            this.handler.handle(callbacks);
            username = nameCB.getName();
            password = pwdCB.getPassword();
         } else {
            if (properties.containsKey(Registry.SASL_USERNAME)) {
               username = (String) properties.get(Registry.SASL_USERNAME);
            } else {
               callbacks = new Callback[1];
               final NameCallback nameCB;
               final String defaultName = System.getProperty("user.name");
               if (defaultName == null) {
                  nameCB = new NameCallback("username: ");
               } else {
                  nameCB = new NameCallback("username: ", defaultName);
               }
               callbacks[0] = nameCB;
               this.handler.handle(callbacks);
               username = nameCB.getName();
            }

            if (properties.containsKey(Registry.SASL_PASSWORD)) {
               password = ((String) properties.get(Registry.SASL_PASSWORD)).toCharArray();
            } else {
               callbacks = new Callback[1];
               final PasswordCallback pwdCB = new PasswordCallback("password: ",false);
               callbacks[0] = pwdCB;
               this.handler.handle(callbacks);
               password = pwdCB.getPassword();
            }
         }

         if (password == null) {
            throw new SaslException("null password supplied");
         }
         final byte[] digest;
         try {
            digest = CramMD5Util.createHMac(password, challenge);
         } catch (InvalidKeyException x) {
            throw new AuthenticationException("evaluateChallenge()", x);
         }
         final String response = username+" "+Util.toString(digest).toLowerCase();
         this.complete = true;

         return response.getBytes("UTF-8");
      } catch (UnsupportedCallbackException x) {
         throw new AuthenticationException("evaluateChallenge()", x);
      } catch (IOException x) {
         throw new AuthenticationException("evaluateChallenge()", x);
      }
   }

   protected String getNegotiatedQOP() {
      return Registry.QOP_AUTH;
   }
}
