/*
 * Project Ren @ 2018
 * Rinkako, Ariana, Gordan. SYSU SDCS.
 */
package org.sysu.renResourcing.principle;

import org.sysu.renResourcing.basic.enums.PrincipleDialectType;

/**
 * Author: Rinkako
 * Date  : 2018/2/6
 * Usage : Parser for task resourcing principle descriptor.
 */
public final class PrincipleParser {

    // set default dialect
    static {
        PrincipleParser.SetParser(PrincipleDialectType.RenSimplePrincipleGrammar);
    }

    /**
     * Parse principle descriptor to RPrinciple object.
     * @param principleDescriptor principle descriptor string
     * @return parsed object
     */
    public static RPrinciple Parse(String principleDescriptor) {
        return PrincipleParser.parser.Parse(principleDescriptor);
    }

    /**
     * Set parser grammar dialect.
     * @param type grammar dialect enum
     */
    public static void SetParser(PrincipleDialectType type) {
        if (PrincipleParser.dialectType != type) {
            PrincipleParser.dialectType = type;
            switch (type) {
                case RenSimplePrincipleGrammar:
                    PrincipleParser.parser = new RenSimplePrincipleGrammar();
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    /**
     * Current parser.
     */
    private static PrincipleGrammar parser = null;

    /**
     * Current parser dialect type.
     */
    private static PrincipleDialectType dialectType = null;
}