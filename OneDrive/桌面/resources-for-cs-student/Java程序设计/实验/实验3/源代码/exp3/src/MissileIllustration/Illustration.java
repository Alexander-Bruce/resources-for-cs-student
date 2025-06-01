package MissileIllustration;

import graphic.Attacker;
import graphic.Interceptor;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;

public interface Illustration {
    default void InterceptorGenerator(HashSet<Interceptor> Interceptors, int speed){
        Interceptor interceptor;
        int x, y;
        x = 610;
        y = 690;
        interceptor = new Interceptor(x, y, speed);
        Interceptors.add(interceptor);
    }

    default void AttackerGenerator(HashSet<Attacker> Attackers, int speed){
        Attacker attacker = null;
        Random random = new Random();
        int diameter = 35; // 攻击者的直径
        int x, y;
        boolean overlap;

        do {
            x = -35;
            y = random.nextInt(650 - diameter); // 生成 y 坐标，保证在界面内

            overlap = false; // 在循环内定义 overlap 变量

            // 检查该坐标是否与现有的 Attacker 有重叠
            for (Attacker existingAttacker : Attackers) {
                int existingX = existingAttacker.getX();
                int existingY = existingAttacker.getY();
                double distance = Math.sqrt(Math.pow(x - existingX, 2) + Math.pow(y - existingY, 2));
                if (distance < diameter) { // 如果距离小于直径，则重叠
                    overlap = true;
                    break;
                }
            }

            // 如果没有重叠，则生成新的 Attacker
            if (!overlap) {
                attacker = new Attacker(x, y, speed);
            }
        } while (overlap);

        Attackers.add(attacker);
    }

    default void AttackerRightGenerator(HashSet<Attacker> Attackers, int speed){
        Attacker attacker = null;
        Random random = new Random();
        int diameter = 35; // 攻击者的直径
        int x, y;
        boolean overlap;

        do {
            x = 1315;
            y = random.nextInt(650 - diameter); // 生成 y 坐标，保证在界面内

            overlap = false; // 在循环内定义 overlap 变量

            // 检查该坐标是否与现有的 Attacker 有重叠
            for (Attacker existingAttacker : Attackers) {
                int existingX = existingAttacker.getX();
                int existingY = existingAttacker.getY();
                double distance = Math.sqrt(Math.pow(x - existingX, 2) + Math.pow(y - existingY, 2));
                if (distance < diameter) { // 如果距离小于直径，则重叠
                    overlap = true;
                    break;
                }
            }

            // 如果没有重叠，则生成新的 Attacker
            if (!overlap) {
                attacker = new Attacker(x, y, speed);
            }
        } while (overlap);

        Attackers.add(attacker);
    }

    default void AttackersMove(HashSet<Attacker> Attackers, HashSet<Attacker> AttackersRight) {
        // 移动所有的 Attackers
        for (Attacker attacker : Attackers)
            attacker.move(1,0);

        // 移动所有的 AttackersRight
        for (Attacker attacker : AttackersRight)
            attacker.move(-1,0);
    }

    default void InterceptorsMove(HashSet<Interceptor> Interceptors) {
        // 移动所有的 Interceptors
        for(Interceptor interceptor : Interceptors)
            interceptor.move(0, -1);
    }

    default int collisionDetection(HashSet<Interceptor> Interceptors, HashSet<Attacker> Attackers, HashSet<Attacker> AttackersRight, int interceptCount) {
        // 存储要删除的 Interceptor
        HashSet<Interceptor> interceptorsToRemove = new HashSet<>();

        // 检测与 Attackers && AttackersRight 的碰撞
        for (Interceptor interceptor : Interceptors) {
            int xInterceptor = interceptor.getX();
            int yInterceptor = interceptor.getY();

            // 检测与 Attackers 的碰撞
            for (Attacker attacker : Attackers) {
                int xAttacker = attacker.getX();
                int yAttacker = attacker.getY();

                // 进行碰撞检测
                if (Math.abs(xInterceptor - xAttacker) < 30 && Math.abs(yInterceptor - yAttacker) < 30) {
                    // 添加要删除的 Interceptor
                    interceptorsToRemove.add(interceptor);
                    Attackers.remove(attacker);
                    interceptCount++;
                    break; // 停止内部循环
                }
            }

            // 检测与 AttackersRight 的碰撞
            for (Attacker attackerRight : AttackersRight) {
                int xAttackerRight = attackerRight.getX();
                int yAttackerRight = attackerRight.getY();

                // 进行碰撞检测
                if (Math.abs(xInterceptor - xAttackerRight) < 30 && Math.abs(yInterceptor - yAttackerRight) < 30) {
                    // 添加要删除的 Interceptor
                    interceptorsToRemove.add(interceptor);
                    AttackersRight.remove(attackerRight);
                    interceptCount++;
                    break; // 停止内部循环
                }
            }
        }

        // 批量移除要删除的 Interceptor
        Interceptors.removeAll(interceptorsToRemove);

        return interceptCount;
    }

    default void deleteAttakersOutOfBound(HashSet<Attacker> Attackers, HashSet<Attacker> AttackersRight) {
        // 删除超出界面范围的 Attacker
        Attackers.removeIf(attacker ->
                attacker.getX() < -35 || attacker.getX() > 1280 ||
                        attacker.getY() < 0 || attacker.getY() > 720
        );

        // 删除超出界面范围的 AttackerRight
        AttackersRight.removeIf(attacker ->
                attacker.getX() < 0 || attacker.getX() > 1315 ||
                        attacker.getY() < 0 || attacker.getY() > 720
        );
    }

    default void deleteInterceptorsOutOfBound(HashSet<Interceptor> Interceptors) {
        // 删除超出界面范围的 Interceptor
        Interceptors.removeIf(interceptor ->
                interceptor.getX() < 0 || interceptor.getX() > 1280 ||
                        interceptor.getY() < 0 || interceptor.getY() > 720
        );
    }

    default int randomGenerator(HashSet<Attacker> Attackers, HashSet<Attacker> AttackersRight, int speed, int generateCount){
        // 生成新的 Attackers
        if(Attackers.size() < 10){
            double number = Math.random() * 30;
            if(number <= 1) {
                AttackerGenerator(Attackers, speed);
                generateCount++;
            }
            if(Attackers.size() <= 2){
                for(int i = 0; i < 2; i++){
                    AttackerGenerator(Attackers, speed);
                    generateCount++;
                }
            }
        }

        // 生成新的 AttackersRight
        if(AttackersRight.size() < 10){
            double number = Math.random() * 30;
            if(number <= 1) {
                AttackerRightGenerator(AttackersRight, speed);
                generateCount++;
            }
            if(AttackersRight.size() <= 2){
                for(int i = 0; i < 2; i++){
                    AttackerRightGenerator(AttackersRight, speed);
                    generateCount++;
                }
            }
        }
        return generateCount;
    }

    default void setMissilesSpeeds(HashSet<Interceptor> Interceptors, HashSet<Attacker> Attackers, HashSet<Attacker> AttackersRight, int speed) {
        // 设置所有 Interceptors 的速度
        for(Interceptor interceptor : Interceptors)
            interceptor.setSpeed(speed);

        // 设置所有 Attackers 的速度
        for(Attacker attacker : Attackers)
            attacker.setSpeed(speed);

        // 设置所有 AttackersRight 的速度
        for(Attacker attacker : AttackersRight)
            attacker.setSpeed(speed);
    }

    default void drawPoint(HashSet<Interceptor> Interceptors, HashSet<Attacker> Attackers, HashSet<Attacker> AttackersRight, Graphics g){
        // 画出所有的 Interceptors
        for (Interceptor point : Interceptors) {
            int x = point.getX();
            int y = point.getY();
            Color color = point.getColor();
            g.setColor(color);
            g.fillOval(x, y, 30, 30);
        }

        // 画出所有的 Attackers
        for(Attacker point : Attackers){
            int x = point.getX();
            int y = point.getY();
            Color color = point.getColor();
            g.setColor(color);
            g.fillOval(x, y, 35, 35);
        }

        // 画出所有的 AttackersRight
        for(Attacker point : AttackersRight){
            int x = point.getX();
            int y = point.getY();
            Color color = point.getColor();
            g.setColor(color);
            g.fillOval(x, y, 35, 35);
        }
    }
}
