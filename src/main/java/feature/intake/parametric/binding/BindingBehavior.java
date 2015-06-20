/*
 * Intake, a command processing library
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) Intake team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package feature.intake.parametric.binding;

import feature.intake.context.CommandLocals;
import feature.intake.parametric.annotation.Switch;
import feature.intake.parametric.argument.ArgumentStack;

/**
 * Determines the type of binding.
 */
public enum BindingBehavior {

    /**
     * Always consumes from a {@link ArgumentStack}.
     */
    CONSUMES,

    /**
     * Sometimes consumes from a {@link ArgumentStack}.
     * <p/>
     * <p>Bindings that exhibit this behavior must be defined as a {@link Switch}
     * by commands utilizing the given binding.</p>
     */
    INDETERMINATE,

    /**
     * Never consumes from a {@link ArgumentStack}.
     * <p/>
     * <p>Bindings that exhibit this behavior generate objects from other sources,
     * such as from a {@link CommandLocals}. These are "magic" bindings that inject
     * variables.</p>
     */
    PROVIDES

}
