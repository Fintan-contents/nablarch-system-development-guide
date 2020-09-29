package com.nablarch.example.proman.common.id;

import nablarch.common.idgenerator.IdFormatter;
import nablarch.common.idgenerator.IdGenerator;
import nablarch.core.repository.SystemRepository;

import java.util.Objects;

/**
 * Utility class for ID numbering.
 * Assign numbers using the ID numbering feature registered to {@link SystemRepository}.
 *
 * @author Tsuyoshi Kawasaki
 * @since 1.0
 */
class IdGeneratorUtil {

    /** Private constructor. */
    private IdGeneratorUtil() {
    }

    /** Key for acquisition of default {@link IdGenerator} */
    private static final String ID_GENERATOR_KEY = "idGenerator";

    /**
     * Performs general-purpose numbering.
     *
     * @param id ID used to identify targets for numbering
     * @return Assigned number
     */
    static String generateId(String id) {
        return getDefaultIdGenerator().generateId(id);
    }

    /**
     * Performs general-purpose numbering.
     *
     * @param id ID used to identify targets for numbering
     * @param idFormatter IdFormatter for formatting numbered IDs
     * @return Assigned number
     */
    static String generateId(String id, IdFormatter idFormatter) {
        return getDefaultIdGenerator().generateId(id, idFormatter);
    }

    /**
     * Acquires default {@link IdGenerator}.
     *
     * @return Implementation of {@link IdGenerator}
     */
    private static IdGenerator getDefaultIdGenerator() {
        return Objects.requireNonNull(SystemRepository.get(ID_GENERATOR_KEY));
    }

}
