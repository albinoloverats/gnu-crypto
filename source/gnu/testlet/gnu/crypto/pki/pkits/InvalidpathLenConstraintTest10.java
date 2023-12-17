/* InvalidpathLenConstraintTest10.java
   Copyright (C) 2003  Free Software Foundation, Inc.

   Distributed under the GPL. See the file `COPYING' */

// Tags: PKITS

package gnu.testlet.gnu.crypto.pki.pkits;

public class InvalidpathLenConstraintTest10 extends BaseInvalidTest
{
  public InvalidpathLenConstraintTest10()
  {
    super(new String[] { "data/certs/InvalidpathLenConstraintTest10EE.crt",
                         "data/certs/pathLenConstraint6subsubCA00Cert.crt",
                         "data/certs/pathLenConstraint6subCA0Cert.crt",
                         "data/certs/pathLenConstraint6CACert.crt" },
          new String[] { "data/crls/pathLenConstraint6subsubCA00CRL.crl",
                         "data/crls/pathLenConstraint6subCA0CRL.crl",
                         "data/crls/pathLenConstraint6CACRL.crl" });
  }
}
