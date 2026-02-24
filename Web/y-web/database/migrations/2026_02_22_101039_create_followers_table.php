<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('followers', function (Blueprint $table) {
            $table->id();
            $table->foreignId('id_follower')
                ->constrained('users')
                ->onDelete('cascade');
            $table->foreignId('id_following')
                ->constrained('users')
                ->onDelete('cascade');
            $table->timestamp('created_at')->useCurrent();

            $table->unique(['id_follower', 'id_following']);
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('followers');
    }
};
